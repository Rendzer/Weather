package com.example.ilonka.pogoda;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentActivity extends FragmentStatePagerAdapter {

    private Configuration configuration;
    private int numberOfFragments = 2;

    private static final float PORTRAIT_ORIENTATION = 1;
    private static final float LANDSCAPE_ORIENTATION = 2;
    private String fragmentTitle[] = {"Sun", "Moon"};

    public FragmentActivity(FragmentManager fragmentManager, Configuration configuration) {
        super(fragmentManager);
        this.configuration = configuration;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SunFragment();
            case 1:
                return new MoonFragment();
        }
        return null;
    }

    @Override
    public float getPageWidth(int position) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            return (1 / LANDSCAPE_ORIENTATION);
        return (1 / PORTRAIT_ORIENTATION);
    }

    @Override
    public int getCount() {
        return numberOfFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle[position];
    }
}
