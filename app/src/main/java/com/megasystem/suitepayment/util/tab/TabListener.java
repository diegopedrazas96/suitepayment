package com.megasystem.suitepayment.util.tab;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fcr on 13/10/2015.
 */
public class TabListener implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs;
    private AppCompatActivity activity;

    public TabListener(ViewPager viewPager, AppCompatActivity activity, ActionBar actionBar, String[] tabs, FragmentPagerAdapter mAdapter) {
        this.viewPager = viewPager;
        this.activity = activity;
        this.actionBar = actionBar;
        this.tabs = tabs;
        this.mAdapter=mAdapter;

    }
    public void init(){
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i < tabs.length; i++) {
            actionBar.addTab(actionBar.newTab().setText(tabs[i]).setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
