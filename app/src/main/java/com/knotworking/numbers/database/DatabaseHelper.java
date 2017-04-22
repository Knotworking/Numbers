package com.knotworking.numbers.database;


public interface DatabaseHelper {
    void addCounterEntry(String name);

    void deleteCounterItem(int id);

    void modifyCount(int id, int change);
}
