package com.knotworking.numbers.database

interface DatabaseHelper {
    fun addCounterEntry(name: String)

    fun deleteCounterItem(id: Int)

    fun modifyCount(id: Int, change: Int)

    fun modifyName(id: Int, newName: String)

    fun saveExchangeRates(rates: Map<String, Float>)

    fun areExchangeRatesInDb(): Boolean
}
