package com.knotworking.numbers.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by BRL on 12/04/17.
 */

public class CounterContract {

    private CounterContract() {}

    public static final String AUTHORITY = "com.example.unitconverter";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class Counters implements BaseColumns {
        public static final String TABLE = "counters";
        public static final String NAME = "name";
        public static final String COUNT = "count";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + Counters.TABLE + " (" +
                        Counters._ID + " INTEGER PRIMARY KEY," +
                        Counters.NAME + " TEXT," +
                        Counters.COUNT + " INTEGER)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Counters.TABLE;

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        CounterContract.CONTENT_URI,
                        "counters");

        /**
         * The mime type of a directory of collections.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        "/counters";

        /**
         * The mime type of a single collection.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        "/counter";
    }


}
