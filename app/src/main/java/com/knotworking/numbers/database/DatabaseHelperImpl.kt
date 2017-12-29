package com.knotworking.numbers.database

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.preference.PreferenceManager
import com.knotworking.numbers.Constants.EXCHANGE_RATE_FETCH_TIME

class DatabaseHelperImpl(private val context: Context) : DatabaseHelper {

    override fun addCounterEntry(name: String) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseContract.Counters.COL_NAME, name)

        context.contentResolver.insert(DatabaseContract.Counters.CONTENT_URI, contentValues)
    }

    override fun deleteCounterItem(id: Int) {
        val uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id))
        context.contentResolver.delete(uri, null, null)
    }

    override fun modifyCount(id: Int, change: Int) {
        val uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id))

        val newCount = getItemCount(id) + change

        val values = ContentValues()
        values.put(DatabaseContract.Counters.COL_COUNT, newCount)

        context.contentResolver.update(uri, values, null, null)
    }

    override fun modifyName(id: Int, newName: String) {
        val uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id))

        val values = ContentValues()
        values.put(DatabaseContract.Counters.COL_NAME, newName)

        context.contentResolver.update(uri, values, null, null)
    }

    private fun getItemCount(id: Int): Int {
        val uri = Uri.withAppendedPath(DatabaseContract.Counters.CONTENT_URI, Integer.toString(id))
        val projection = arrayOf(DatabaseContract.Counters.COL_COUNT)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(DatabaseContract.Counters.COL_COUNT))
        }

        cursor?.close()

        return 0
    }

    override fun saveExchangeRates(rates: Map<String, Float>) {

        rates.forEach({
            replaceExchangeRate(currency = it.key, rate = it.value)
        })

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putLong(EXCHANGE_RATE_FETCH_TIME, System.currentTimeMillis()).apply()
    }

    private fun replaceExchangeRate(currency: String, rate: Float) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseContract.ExchangeRates.COL_CURRENCY, currency)
        contentValues.put(DatabaseContract.ExchangeRates.COL_RATE, rate)

        context.contentResolver.insert(DatabaseContract.ExchangeRates.CONTENT_URI, contentValues)
    }
}