package edu.uuerp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import edu.uuerp.databinding.FragmentErpBinding;
import java.util.zip.Inflater;

public class ERPFragment extends Fragment {

    private FragmentErpBinding FragmentERPView;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    public android.view.View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle arg2) {
        FragmentERPView = FragmentErpBinding.inflate(inflater, container, false);
        webView = FragmentERPView.webView;
        progressBar = FragmentERPView.progressBar;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        
        webView.setWebViewClient(
                new WebViewClient() {
                    boolean loadingFinished = true, redirect = false;

                    @Override
                    public boolean shouldOverrideUrlLoading(
                            WebView view, WebResourceRequest request) {
                        if (!loadingFinished) {
                            redirect = true;
                        }

                        loadingFinished = false;
                        webView.loadUrl(request.getUrl().toString());
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        loadingFinished = false;
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        if (!redirect) {
                            loadingFinished = true;
                            progressBar.setVisibility(View.GONE);
                        } else {
                            redirect = false;
                        }
                    }
                });

        webView.setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int progress) {
                        progress = progress * 100;
                        progressBar.setProgress(progress);
                    }
                });

        return FragmentERPView.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle arg1) {
        webView.loadUrl("https://erp.uttarauniversity.edu.bd");
    }
}
