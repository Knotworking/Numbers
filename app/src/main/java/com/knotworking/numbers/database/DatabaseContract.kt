package com.knotworking.numbers.database

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by BRL on 12/04/17.
 */

object DatabaseContract {

    val AUTHORITY = "com.knotworking.numbers"

    val CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY)

    class Counters : BaseColumns {
        companion object {
            val TABLE = "counters"
            val COL_ID = BaseColumns._ID
            val COL_NAME = "name"
            val COL_COUNT = "count"

            val CREATE_TABLE = "CREATE TABLE " + Counters.TABLE + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME + " TEXT," +
                    COL_COUNT + " INTEGER DEFAULT 0)"

            val DELETE_TABLE = "DROP TABLE IF EXISTS " + Counters.TABLE

            /**
             * The content URI for this table.
             */
            val CONTENT_URI: Uri = Uri.withAppendedPath(
                    DatabaseContract.CONTENT_URI,
                    "counters")

            /**
             * The mime type of a directory of collections.
             */
            val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/counters"

            /**
             * The mime type of a single collection.
             */
            val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/counter"
        }
    }

    class ExchangeRates : BaseColumns {
        companion object {
            val TABLE = "exchange_rates"
            val COL_ID = BaseColumns._ID
            val COL_CURRENCY = "currency"
            val COL_RATE = "rate"

            val CREATE_TABLE = "CREATE TABLE " + ExchangeRates.TABLE + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_CURRENCY + " TEXT UNIQUE," +
                    COL_RATE + " REAL)"

            val DELETE_TABLE = "DROP TABLE IF EXISTS " + ExchangeRates.TABLE

            /**
             * The content URI for this table.
             */
            val CONTENT_URI: Uri = Uri.withAppendedPath(
                    DatabaseContract.CONTENT_URI,
                    "exchange_rates")

            /**
             * The mime type of a directory of exchange rates.
             */
            val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/exchange_rates"

            /**
             * The mime type of a single exchange rate.
             */
            val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/exchange_rate"
        }
    }

}
