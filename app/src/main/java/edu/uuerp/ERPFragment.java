package edu.uuerp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import edu.uuerp.databinding.FragmentErpBinding;
import java.util.zip.Inflater;

public class ERPFragment extends Fragment {

    private FragmentErpBinding FragmentERPView;
    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;

    private ERPCustomInjections erpInjections;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
        FragmentERPView = FragmentErpBinding.inflate(inflater, container, false);

        webView = FragmentERPView.webView;
        progressBar = FragmentERPView.progressBar;
        swipeRefresh = FragmentERPView.swipeRefreshView;

        swipeRefresh.setOnRefreshListener(() -> webView.loadUrl(webView.getUrl()));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        CookieManager.getInstance().setAcceptCookie(true);

        erpInjections = new ERPCustomInjections(requireContext(), webView);

        webView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(
                            WebView view, WebResourceRequest request) {
                        if (request.getUrl().getHost().contains("uttarauniversity.edu.bd"))
                            return false;
                        Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                        view.getContext().startActivity(intent);
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    
                    @Override
                    public void onPageCommitVisible(WebView view, String url){
                       erpInjections.attemptCustomInjections(view, url); 
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        progressBar.setVisibility(View.GONE);
                        swipeRefresh.setRefreshing(false);
                    }
                });

        webView.setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int progress) {
                        progressBar.setProgress(progress);
                    }
                });

        return FragmentERPView.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle arg1) {
        webView.loadUrl(
                "https://erp.uttarauniversity.edu.bd/SSMVC/Account/Account/LoginJSValidation");
    }
}
