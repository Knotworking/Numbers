package com.knotworking.numbers.converter

import android.database.Cursor
import com.knotworking.numbers.database.DatabaseContract

/**
 * Created on 28.12.17.
 */
object ExchangeRateCursorConverter {
    fun getData(cursor: Cursor): Map<String, Float> {
        val exchangeRates: MutableMap<String, Float> = mutableMapOf()

        if (cursor.moveToFirst()) {
            do {
                val currency = cursor.getString(cursor.getColumnIndex(DatabaseContract.ExchangeRates.COL_CURRENCY))
                val rate = cursor.getFloat(cursor.getColumnIndex(DatabaseContract.ExchangeRates.COL_RATE))
                exchangeRates.put(currency, rate)
            } while (cursor.moveToNext())
        }

        return exchangeRates
    }
}