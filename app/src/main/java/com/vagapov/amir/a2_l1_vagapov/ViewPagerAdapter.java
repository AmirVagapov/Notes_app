package com.vagapov.amir.a2_l1_vagapov;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> fragmentTitle = new ArrayList<>();

    ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        fragmentTitle.add(title);
    }

    void replaceFirstFragment(Fragment fragment, String title){
        mFragments.remove(0);
        fragmentTitle.remove(0);
        mFragments.add(0, fragment);
        fragmentTitle.add(0, title);
    }
}
