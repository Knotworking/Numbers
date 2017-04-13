package com.knotworking.numbers.counter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotworking.numbers.R;

import java.util.ArrayList;
import java.util.List;

public class CounterFragment extends Fragment {
    RecyclerView recyclerView;
    CounterAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_counter, container, false);
        recyclerView = (RecyclerView)root.findViewById(R.id.fragment_counter_recyclerView);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CounterAdapter();
        adapter.setData(getDummyData());
        recyclerView.setAdapter(adapter);
    }

    private List<CounterItem> getDummyData() {
        List<CounterItem> data = new ArrayList<>();

        data.add(new CounterItem("Snakes", 500));
        data.add(new CounterItem("Bottles of beer on the wall", 99));
        data.add(new CounterItem("High fives", 0));
        return data;
    }
}
