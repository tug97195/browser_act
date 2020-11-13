package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class PageControlFragment extends Fragment {
    PageControlInterface browserActivity;
    ImageButton goButton, forwardButton, backButton;
    TextView urlTextView;

    public PageControlFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageControlInterface) {
            browserActivity = (PageControlInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_page_control, container, false);

        urlTextView = v.findViewById(R.id.url_text_input);
        goButton = v.findViewById(R.id.goButton);
        forwardButton = v.findViewById(R.id.ForwardButton);
        backButton = v.findViewById(R.id.BackButton);

        View.OnClickListener controlOcl = new View.OnClickListener() {
            @Override
            public void onClick(View i) {
                if (i.equals(goButton)){
                    browserActivity.go(formatUrl(urlTextView.getText().toString()));
                }
                else if (i.equals(forwardButton)){
                    browserActivity.forward();
                }
                else if (i.equals(backButton)){
                    browserActivity.back();
                }
            }
        };

        goButton.setOnClickListener(controlOcl);
        forwardButton.setOnClickListener(controlOcl);
        backButton.setOnClickListener(controlOcl);

        return v;
    }

    public void updateUrl(String url) {
        urlTextView.setText(url);
    }

    /**
     * Format URL to ensure a protocol is specified
     * @param url that is checked for protocol
     * @return url with protocol prefixed if not found
     */
    private String formatUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "http://" + url;
        } else {
            return url;
        }
    }

    interface PageControlInterface {
        void go(String url);
        void forward();
        void back();
    }
}