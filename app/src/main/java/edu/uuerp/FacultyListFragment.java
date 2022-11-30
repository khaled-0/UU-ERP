package edu.uuerp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import edu.uuerp.databinding.FragmentFacultyListBinding;
import java.util.ArrayList;
import org.json.JSONObject;

public class FacultyListFragment extends Fragment {

    private FragmentFacultyListBinding FragmentFacultyListView;
    private RecyclerView facultyRecyclerView;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;

    private FacultyListRecyclerAdapter facultyListAdapter;

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
    public void onViewCreated(View view, Bundle arg1) {
        loadFacultyMembersData(getContext());
        swipeRefresh.setOnRefreshListener(
                () -> {
                    loadFacultyMembersData(getContext());
                });
    }

    private void loadFacultyMembersData(Context context) {
        swipeRefresh.setRefreshing(true);
        ArrayList<FacultyMember> facultyList = new ArrayList<>();

        new Thread(
                        () -> {

                            // TODO:OkHTTP from github

                            // facultyList.add();

                            if (getActivity() == null) return;
                            getActivity()
                                    .runOnUiThread(
                                            () -> {
                                                facultyListAdapter =
                                                        new FacultyListRecyclerAdapter(
                                                                getContext(), facultyList);
                                                facultyRecyclerView.setAdapter(facultyListAdapter);
                                                swipeRefresh.setRefreshing(false);
                                            });
                        })
                .start();
    }
}
