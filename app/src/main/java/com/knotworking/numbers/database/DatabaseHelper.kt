package com.knotworking.numbers.database

import com.knotworking.numbers.converter.HistoryItem

interface DatabaseHelper {
    fun addCounterEntry(name: String)

    fun deleteCounterItem(id: Int)

    fun modifyCount(id: Int, change: Int)

    fun modifyName(id: Int, newName: String)

    fun saveExchangeRates(rates: Map<String, Float>)

    fun areExchangeRatesInDb(): Boolean

    fun addConversionHistoryItem(item: HistoryItem)

    fun deleteConversionHistoryItem(id: Int)
}
