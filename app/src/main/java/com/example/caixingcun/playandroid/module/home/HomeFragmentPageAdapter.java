package com.example.caixingcun.playandroid.module.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by caixingcun on 2018/3/14.
 */

public class HomeFragmentPageAdapter extends FragmentPagerAdapter {

    private FragmentManager mManager;
    List<Fragment> mFragmentList;
    public HomeFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
        mManager = fm;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

}
