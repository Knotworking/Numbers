package com.knotworking.numbers.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by BRL on 12/04/17.
 */

public class DatabaseContract {

    private DatabaseContract() {}

    public static final String AUTHORITY = "com.knotworking.numbers";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class Counters implements BaseColumns {
        public static final String TABLE = "counters";
        public static final String COL_NAME = "name";
        public static final String COL_COUNT = "count";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + Counters.TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COL_NAME + " TEXT," +
                        COL_COUNT + " INTEGER DEFAULT 0)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Counters.TABLE;

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DatabaseContract.CONTENT_URI,
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

    public static class ExchangeRates implements BaseColumns {
        public static final String TABLE = "exchange_rates";
        public static final String COL_CURRENCY = "currency";
        public static final String COL_RATE = "rate";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + ExchangeRates.TABLE + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COL_CURRENCY + " TEXT UNIQUE," +
                        COL_RATE + " REAL)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + ExchangeRates.TABLE;

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(
                        DatabaseContract.CONTENT_URI,
                        "exchange_rates");

        /**
         * The mime type of a directory of exchange rates.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        "/exchange_rates";

        /**
         * The mime type of a single exchange rate.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        "/exchange_rate";
    }

}
