package com.example.brl.unitconverter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    NumbersPagerAdapter adapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new NumbersPagerAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.activity_view_pager);
        pager.setAdapter(adapter);

    }


}
