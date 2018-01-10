package com.thanhclub.musicdemo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thanhclub.musicdemo.ui.AllSongFragment;
import com.thanhclub.musicdemo.ui.FavoriteFragment;



public class TabPagerApdapter extends FragmentPagerAdapter {
    private final int TAB_COUNT = 2;

    public TabPagerApdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new AllSongFragment();
                break;
            case 1:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence sequence = null;
        switch (position){
            case 0:
                sequence = "SONG";
                break;
            case 1:
                sequence = "FAVORITE";
                break;

        }
        return sequence;
    }
}
