package com.knotworking.numbers

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.knotworking.numbers.Constants.CONVERTER_TAB
import com.knotworking.numbers.Constants.COUNTER_TAB

import com.knotworking.numbers.counter.CounterFragment

/**
 * Created by BRL on 25/03/17.
 */

class NumbersPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            CONVERTER_TAB -> ConverterFragment()
            COUNTER_TAB -> CounterFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
