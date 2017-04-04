package com.example.brl.unitconverter.counter;

public class CounterItem {
    private String title;
    private int count;

    public CounterItem() {
    }

    public CounterItem(String title, int count) {
        this.count = count;
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
