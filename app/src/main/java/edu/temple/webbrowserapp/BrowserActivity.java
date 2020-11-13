package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements
        PageControlFragment.PageControlInterface,
        PagerFragment.PagerInterface,
        BrowserControlFragment.BrowserControlInterface,
        PageViewerFragment.PageViewerInterface,
        PageListFragment.PageListInterface {

    FragmentManager fm;
    PageControlFragment controlFrag;
    PagerFragment pager;
    BrowserControlFragment browserControl;
    PageListFragment pageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        Fragment tmpFragment;

        if ((tmpFragment = fm.findFragmentById(R.id.page_control_layout)) instanceof PageControlFragment) {
            controlFrag = (PageControlFragment) tmpFragment;
        }
        else {
            controlFrag = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control_layout, controlFrag)
                    .commit();
        }

        if ((tmpFragment = fm.findFragmentById(R.id.page_display_layout)) instanceof PagerFragment) {
            pager = (PagerFragment) tmpFragment;
        }
        else {
            pager = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.page_display_layout, pager)
                    .commit();
        }

        if ((tmpFragment = fm.findFragmentById(R.id.browser_control_layout)) instanceof BrowserControlFragment) {
            browserControl = (BrowserControlFragment) tmpFragment;
        }
        else {
            browserControl = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control_layout, browserControl)
                    .commit();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If fragment already added (activity restarted) then hold reference
            // otherwise add new fragment. Only one instance of fragment is ever present
            if ((tmpFragment = fm.findFragmentById(R.id.page_list_layout)) instanceof PageListFragment) {
                pageList = (PageListFragment) tmpFragment;
            }
            else {
                pageList = new PageListFragment();
                fm.beginTransaction()
                        .add(R.id.page_list_layout, pageList)
                        .commit();
            }
        }

    }

    /**
     * Update WebPage whenever PageControlFragment sends new Url
     * @param url to load
     */
    @Override
    public void go(String url) {
        pager.go(url);
    }

    /**
     * Go back to previous page when user presses Back in PageControlFragment
     */
    @Override
    public void back() {
        pager.back();
    }

    /**
     * Go back to next page when user presses Forward in PageControlFragment
     */
    @Override
    public void forward() {
        pager.forward();
    }

    /**
     * Update displayed Url in PageControlFragment when Webpage Url changes
     * @param url to display
     */
    @Override
    public void updateUrl(String url) {
        controlFrag.updateUrl(url);
    }

    @Override
    public void updateName(String name) {
        BrowserActivity.this.setTitle(name);
    }

    @Override
    public void newTab() {
        pager.newTab();
        controlFrag.updateUrl("");
        if (BrowserActivity.this.getTitle().equals("")) {
            BrowserActivity.this.setTitle("WebBrowserApp");
        }
    }
}

