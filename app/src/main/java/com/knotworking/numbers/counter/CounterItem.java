package com.knotworking.numbers.counter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.knotworking.numbers.BR;

public class CounterItem extends BaseObservable {
    private int id;
    private String name;
    private int count;

    public CounterItem(int id, String name, int count) {
        this.id = id;
        this.count = count;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CounterItem)) return false;

        CounterItem item = (CounterItem) obj;
        if (!this.getName().equals(item.getName())) return false;
        if (this.getCount() != item.getCount()) return false;

        return id == item.getId();
    }
}
