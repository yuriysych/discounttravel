package com.androidapp.ysych.discounttravel.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidapp.ysych.discounttravel.fragments.SlideFragment;

public class SlidesPagerAdapter extends FragmentPagerAdapter {

    String[] slides;

    public SlidesPagerAdapter(FragmentManager fm, String[] slides) {
        super(fm);
        this.slides = slides;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("image", slides[position]);
        bundle.putInt("count", slides.length);
        Fragment fragment = new SlideFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return slides.length;
    }
}