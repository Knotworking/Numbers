package com.example.brl.unitconverter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by BRL on 25/03/17.
 */

public class NumbersPagerAdapter extends FragmentPagerAdapter {

    public NumbersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ConverterFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
