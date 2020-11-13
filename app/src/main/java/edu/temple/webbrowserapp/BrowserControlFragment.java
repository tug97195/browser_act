package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {

    private BrowserControlInterface browser;
    private ImageButton newTabB;


    // Save reference to parent
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControlInterface) {
            browser = (BrowserControlInterface) context;
        } else {
            throw new RuntimeException("Required interface has not been implemented");
        }
    }

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser_control, container, false);

        newTabB = v.findViewById(R.id.newTab);

        newTabB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browser.newTab();
            }
        });
        return v;
    }

    interface BrowserControlInterface {
        void newTab();
    }
}