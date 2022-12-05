package edu.uuerp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import edu.uuerp.FacultyMember;
import edu.uuerp.databinding.FragmentFacultyListBinding;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacultyListFragment extends Fragment {

    private FragmentFacultyListBinding FragmentFacultyListView;
    private RecyclerView facultyRecyclerView;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;

    private FacultyListRecyclerAdapter facultyListAdapter;
    private ArrayList<FacultyMember> facultyList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
        FragmentFacultyListView = FragmentFacultyListBinding.inflate(inflater, container, false);
        facultyRecyclerView = FragmentFacultyListView.facultyRecyclerView;
        searchView =
                (SearchView)
                        FragmentFacultyListView.toolBar
                                .getMenu()
                                .findItem(R.id.action_search)
                                .getActionView();
        swipeRefresh = FragmentFacultyListView.swipeRefreshView;

        return FragmentFacultyListView.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle arg) {
        loadFacultyMembersData(getContext());
        swipeRefresh.setOnRefreshListener(
                () -> {
                    searchView.setIconified(true);
                    loadFacultyMembersData(getContext());
                });

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        filterFacultyData(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterFacultyData(newText);
                        return false;
                    }
                });
    }

    private void filterFacultyData(String additionalQuery) {
        if (additionalQuery.isEmpty()) facultyListAdapter.updateData(facultyList);

        final ArrayList<FacultyMember> filteredFacultyList = new ArrayList<>();
        for (FacultyMember member : facultyList)
            if (member.matchesQuery(additionalQuery)) filteredFacultyList.add(member);
        facultyListAdapter.updateData(filteredFacultyList);
    }

    private void saveFacultyData(JSONArray jsonData, Context context) throws IOException {
        File file = new File(context.getCacheDir(), "facultyData.json");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(jsonData.toString().getBytes());
        fos.close();
    }

    private ArrayList<FacultyMember> getSavedFacultyData(Context context) {
        ArrayList<FacultyMember> data = new ArrayList<>();

        try {
            File file = new File(context.getCacheDir(), "facultyData.json");

            int length = (int) file.length();
            byte[] bytes = new byte[length];

            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();

            String contents = new String(bytes);

            JSONArray facultyDataArray = new JSONArray(new String(bytes));
            for (int i = 0; i < facultyDataArray.length(); i++)
                data.add(new FacultyMember(facultyDataArray.getJSONObject(i)));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void loadFacultyMembersData(Context context) {
        swipeRefresh.setRefreshing(true);

        new Thread(
                        () -> {
                            try {
                                final String facultyDataUrl =
                                        "https://raw.githubusercontent.com/khaled-0/UU-ERP/main/assets/facultyList.json";

                                Response facultyDataResponse =
                                        new OkHttpClient()
                                                .newCall(
                                                        new Request.Builder()
                                                                .url(facultyDataUrl)
                                                                .get()
                                                                .build())
                                                .execute();

                                if (!facultyDataResponse.isSuccessful())
                                    throw new IOException(facultyDataResponse.message());

                                JSONArray facultyDataArray =
                                        new JSONArray(facultyDataResponse.body().string());

                                facultyList.clear();
                                for (int i = 0; i < facultyDataArray.length(); i++)
                                    facultyList.add(
                                            new FacultyMember(facultyDataArray.getJSONObject(i)));

                                facultyDataResponse.close();
                                saveFacultyData(facultyDataArray, context);
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                                facultyList.addAll(getSavedFacultyData(context));
                                if (getActivity() != null)
                                    requireActivity()
                                            .runOnUiThread(
                                                    () -> {
                                                        Toast.makeText(
                                                                        requireActivity(),
                                                                        "Failed to load latest data.",
                                                                        Toast.LENGTH_SHORT)
                                                                .show();
                                                    });
                            }

                            if (getActivity() != null)
                                requireActivity()
                                        .runOnUiThread(
                                                () -> {
                                                    facultyListAdapter =
                                                            new FacultyListRecyclerAdapter(
                                                                    getContext(), facultyList);
                                                    facultyRecyclerView.setAdapter(
                                                            facultyListAdapter);
                                                    swipeRefresh.setRefreshing(false);
                                                });
                        })
                .start();
    }
}
