package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment implements PageViewerFragment.PageViewerInterface {

    FragmentManager fm;
    MyPagerAdapter pager_adapter;
    ViewPager view_pager;
    PagerInterface browser_activity;
    PageViewerFragment current_page;
    ArrayList<PageViewerFragment> page_list = new ArrayList<>();

    int num_pages = 1;
    int current_posi = 0;

    public PagerFragment() {}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("PAGE_LIST", page_list);
        outState.putInt("NUM_PAGES", num_pages);
    }

    // Save reference to parent
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PagerInterface) {
            browser_activity = (PagerInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_pager, container, false);

        if (savedInstanceState != null) {
            page_list = (ArrayList) savedInstanceState.getSerializable("PAGE_LIST");
            num_pages = savedInstanceState.getInt("NUM_PAGES");
        }

        fm = getChildFragmentManager();
        Fragment tmpFragment;

        // If fragment already added (activity restarted) then hold reference
        // otherwise add new fragment. Only one instance of fragment is ever present
        if ((tmpFragment = fm.findFragmentById(R.id.pager_display_layout)) instanceof PageControlFragment) {
            current_page = (PageViewerFragment) tmpFragment;
        }
        else {
            current_page = new PageViewerFragment();
            fm.beginTransaction()
                    .add(R.id.pager_display_layout, current_page)
                    .commit();
        }

        pager_adapter = new MyPagerAdapter(fm);
        view_pager = l.findViewById(R.id.pager_display_layout);
        view_pager.setAdapter(pager_adapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                browser_activity.updateUrl(page_list.get(position).getURL());
                browser_activity.updateName(page_list.get(position).getName());
                current_posi = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return l;
    }

    public void newTab() {
        num_pages++;
        PageViewerFragment newTab = new PageViewerFragment();
        page_list.add(newTab);
        pager_adapter.notifyDataSetChanged();
        view_pager.setCurrentItem(num_pages);

    }

    /**
     * Load provided URL in webview
     * @param url to load
     */
    public void go (String url) {
        page_list.get(current_posi).go(url);
    }

    /**
     * Go to next page
     */
    public void forward () {
        page_list.get(current_posi).forward();
    }

    /**
     * Go to previous page
     */
    public void back () {
        page_list.get(current_posi).back();
    }

    @Override
    public void updateUrl(String url) {
        browser_activity.updateUrl(url);
    }

    @Override
    public void updateName(String name) {
        browser_activity.updateName(name);
    }


    interface PagerInterface {
        void updateUrl(String url);
        void updateName(String name);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fm) { super(fm); }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (page_list.size() == 0) {
                PageViewerFragment newTab = new PageViewerFragment();
                page_list.add(newTab);
                view_pager.setCurrentItem(position);
                return page_list.get(position);
            }
            else {
                return page_list.get(position);
            }
        }

        @Override
        public int getCount() {
            return num_pages;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (page_list.contains(object))
                return page_list.indexOf(object);
            else
                return POSITION_NONE;
        }
    }
}

