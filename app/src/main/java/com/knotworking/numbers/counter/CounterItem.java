package com.knotworking.numbers.counter;

public class CounterItem {
    private String name;
    private int count;

    public CounterItem() {
    }

    public CounterItem(String name, int count) {
        this.count = count;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
