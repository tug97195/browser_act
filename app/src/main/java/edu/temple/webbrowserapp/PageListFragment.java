package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    PageListInterface browser_activity;
    ArrayList<String> page_history = new ArrayList<>();

    public PageListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageListInterface) {
            browser_activity = (PageListInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_list, container, false);
        ListView listName = v.findViewById(R.id.page_list_view);
        listName.setAdapter(new listAdapter((Context) browser_activity, page_history));
        return v;
    }
    interface PageListInterface {}

    public void getList(ArrayList<String> list) {
        page_history = list;
    }

    private class listAdapter extends BaseAdapter {

        private final ArrayList<String> list;
        private final Context context;

        private listAdapter(Context context, ArrayList<String> list) {
            this.list = list;
            this.context = context;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(context);
            textView.setText(getItem(i).toString());
            return textView;
        }
    }

}