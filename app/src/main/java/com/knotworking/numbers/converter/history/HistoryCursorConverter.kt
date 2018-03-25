package com.knotworking.numbers.converter.history

import android.database.Cursor
import com.knotworking.numbers.converter.ConversionItem
import com.knotworking.numbers.database.DatabaseContract

/**
 * Created on 24.03.18.
 */
object HistoryCursorConverter {
    fun getData(cursor: Cursor): List<ConversionItem> {
        val historyItems: MutableList<ConversionItem> = mutableListOf()

        if (cursor.moveToFirst()) {
            do {
                val unitType = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ConversionHistory.COL_UNIT_TYPE_CODE))
                val inputType = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ConversionHistory.COL_INPUT_UNIT_CODE))
                val inputValue = cursor.getFloat(cursor.getColumnIndex(DatabaseContract.ConversionHistory.COL_INPUT_VALUE))
                val outputType = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ConversionHistory.COL_OUTPUT_UNIT_CODE))
                val outputValue = cursor.getFloat(cursor.getColumnIndex(DatabaseContract.ConversionHistory.COL_OUTPUT_VALUE))
                historyItems.add(ConversionItem(unitType, inputType, inputValue, outputType, outputValue))
            } while (cursor.moveToNext())
        }

        return historyItems
    }
}