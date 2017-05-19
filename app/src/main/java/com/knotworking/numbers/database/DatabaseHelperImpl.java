package com.knotworking.numbers.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class DatabaseHelperImpl implements DatabaseHelper {

    private Context context;

    public DatabaseHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addCounterEntry(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Counters.COL_NAME, name);

        context.getContentResolver().insert(DatabaseContract.Counters.CONTENT_URI, contentValues);
    }

    @Override
    public void deleteCounterItem(int id) {
        Uri uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id));
        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void modifyCount(int id, int change) {
        Uri uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id));

        int newCount = getItemCount(id) + change;

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Counters.COL_COUNT, newCount);

        context.getContentResolver().update(uri, values, null, null);
    }

    @Override
    public void modifyName(int id, String newName) {
        Uri uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id));

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Counters.COL_NAME, newName);

        context.getContentResolver().update(uri, values, null, null);
    }

    private int getItemCount(int id) {
        Uri uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id));
        String[] projection = new String[]{DatabaseContract.Counters.COL_COUNT};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(DatabaseContract.Counters.COL_COUNT));
        }

        if (cursor != null) {
            cursor.close();
        }

        return 0;
    }

}
