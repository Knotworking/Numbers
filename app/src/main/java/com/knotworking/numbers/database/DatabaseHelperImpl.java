package com.knotworking.numbers.database;

import android.content.ContentValues;
import android.content.Context;

public class DatabaseHelperImpl implements DatabaseHelper {

    private Context context;

    public DatabaseHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addCounterEntry(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CounterContract.Counters.NAME, name);

        context.getContentResolver().insert(CounterContract.Counters.CONTENT_URI, contentValues);
    }

}
