package com.knotworking.numbers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.knotworking.numbers.counter.CounterFragment;

import static com.knotworking.numbers.Constants.CONVERTER_TAB;
import static com.knotworking.numbers.Constants.COUNTER_TAB;

/**
 * Created by BRL on 25/03/17.
 */

public class NumbersPagerAdapter extends FragmentPagerAdapter {

    public NumbersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CONVERTER_TAB:
                return new ConverterFragment();
            case COUNTER_TAB:
                return new CounterFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
