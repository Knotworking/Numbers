package com.knotworking.numbers.counter;

import android.database.Cursor;

import com.knotworking.numbers.database.DatabaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BRL on 17/04/17.
 */

public class CounterCursorConverter {

    public static List<CounterItem> getData (Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        List<CounterItem> counterItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Counters._ID));
                String entryName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Counters.COL_NAME));
                int entryCount = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Counters.COL_COUNT));
                counterItems.add(new CounterItem(id, entryName, entryCount));
            } while (cursor.moveToNext());
        }

        return counterItems;
    }

}
