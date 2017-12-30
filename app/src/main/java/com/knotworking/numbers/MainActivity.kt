package com.knotworking.numbers

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.knotworking.numbers.Constants.CONVERTER_TAB
import com.knotworking.numbers.Constants.COUNTER_TAB
import com.knotworking.numbers.Constants.EXCHANGE_RATE_FETCH_TIME
import com.knotworking.numbers.api.CurrencyApi
import com.knotworking.numbers.counter.CreateItemDialog
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var adapter: NumbersPagerAdapter? = null
    private var pager: ViewPager? = null
    private var fab: FloatingActionButton? = null

    //TODO inject singleton
    private val currencyApi = CurrencyApi(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NumbersPagerAdapter(supportFragmentManager)
        pager = findViewById<View>(R.id.activity_view_pager) as ViewPager
        pager?.adapter = adapter
        fab = findViewById<View>(R.id.activity_fab) as FloatingActionButton
        fab?.setOnClickListener(this)

        setupActionBar()
        checkExchangeRates()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.navigationMode = ActionBar.NAVIGATION_MODE_TABS
        actionBar?.setDisplayShowTitleEnabled(false)

        val tabListener = object : ActionBar.TabListener {
            override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                pager?.currentItem = tab.position
                if (tab.position == COUNTER_TAB) {
                    fab?.show()
                } else {
                    fab?.hide()
                }
            }

            override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) {

            }

            override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) {

            }
        }

        for (i in 0..1) {
            actionBar?.addTab(actionBar.newTab()
                    .setText(getTabTitle(i))
                    .setTabListener(tabListener))
        }

        pager!!.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                actionBar?.setSelectedNavigationItem(position)
            }
        })
    }

    private fun getTabTitle(position: Int): Int {
        when (position) {
            CONVERTER_TAB -> return R.string.first_tab
            COUNTER_TAB -> return R.string.second_tab
            else -> {
                Log.e(TAG, "tab position not recognised")
                return 0
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.activity_fab) {
            showNewItemDialog()
        }
    }

    private fun showNewItemDialog() {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(NEW_ITEM_DIALOG)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        val dialog = CreateItemDialog()
        dialog.show(ft, NEW_ITEM_DIALOG)
    }

    private fun checkExchangeRates() {

        //TODO areExchangeRatesInDb

        //TODO refactor into new method
        val fetchThreshold = TimeUnit.DAYS.toMillis(7)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val lastFetchTimeStamp = sharedPreferences.getLong(EXCHANGE_RATE_FETCH_TIME, 0)
        val now = System.currentTimeMillis()
        val diff = now - lastFetchTimeStamp

        if (diff > fetchThreshold) {
            currencyApi.getExchangeRates()
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val NEW_ITEM_DIALOG = "new_item_dialog"
    }
}
