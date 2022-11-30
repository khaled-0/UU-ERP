package edu.uuerp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import edu.uuerp.databinding.FragmentAboutBinding;
import java.util.zip.Inflater;


public class AboutFragment extends Fragment {
    
    private FragmentAboutBinding FragmentAboutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
       FragmentAboutView = FragmentAboutBinding.inflate(inflater,container,false);
         
       return FragmentAboutView.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle arg1) {

    }
}
