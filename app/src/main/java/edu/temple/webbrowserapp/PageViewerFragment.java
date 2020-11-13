package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {

    private WebView wv;
    private PageViewerInterface parent_activity;

    public PageViewerFragment() {
    }

    // Save reference to parent
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageViewerInterface) {
            parent_activity = (PageViewerInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View l = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        wv = l.findViewById(R.id.WebView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Inform parent activity that URL is changing
                parent_activity.updateUrl(url);
                String name = view.getTitle();
                parent_activity.updateName(name);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String name = view.getTitle();
                parent_activity.updateName(name);
            }
        });


        if (savedInstanceState != null) {
            wv.restoreState(savedInstanceState);
        }
        return l;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        wv.saveState(outState);
    }

    public String getURL() {
        return wv.getUrl();
    }

    public String getName() { return wv.getTitle();}

    /**
     * Load provided URL in webview
     * @param url to load
     */
    public void go (String url) {
        wv.loadUrl(url);
    }

    /**
     * Go to previous page
     */
    public void back () {
        wv.goBack();
    }

    /**
     * Go to next page
     */
    public void forward () {
        wv.goForward();
    }

    interface PageViewerInterface {
        void updateUrl(String url);
        void updateName(String name);
    }
}