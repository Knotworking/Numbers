package com.knotworking.numbers.counter;

public class CounterItem {
    private int id;
    private String name;
    private int count;

    public CounterItem() {
    }

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
