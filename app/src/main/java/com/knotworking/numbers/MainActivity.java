package com.knotworking.numbers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.knotworking.numbers.counter.CreateItemDialog;
import com.knotworking.numbers.database.DatabaseHelper;
import com.knotworking.numbers.database.DatabaseHelperImpl;

import static com.knotworking.numbers.Constants.CONVERTER_TAB;
import static com.knotworking.numbers.Constants.COUNTER_TAB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String NEW_ITEM_DIALOG = "new_item_dialog";

    private NumbersPagerAdapter adapter;
    private ViewPager pager;
    private FloatingActionButton fab;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelperImpl(this);
        adapter = new NumbersPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.activity_view_pager);
        pager.setAdapter(adapter);
        fab = (FloatingActionButton)findViewById(R.id.activity_fab);
        fab.setOnClickListener(this);

        setupActionBar();

    }

    private void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == COUNTER_TAB) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        for (int i = 0; i < 2; i++) {
            actionBar.addTab(actionBar.newTab()
            .setText(getTabTitle(i))
            .setTabListener(tabListener));
        }

        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
    }

    private int getTabTitle(int position) {
        switch (position) {
            case CONVERTER_TAB:
                return R.string.first_tab;
            case COUNTER_TAB:
                return R.string.second_tab;
            default:
                Log.e(TAG, "tab position not recognised");
                return 0;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_fab) {
            showNewItemDialog();
        }
    }

    private void showNewItemDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(NEW_ITEM_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        CreateItemDialog dialog = new CreateItemDialog();
        dialog.show(ft, NEW_ITEM_DIALOG);
    }
}
