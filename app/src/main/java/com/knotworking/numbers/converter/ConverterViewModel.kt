package com.knotworking.numbers.converter

import com.knotworking.numbers.converter.history.HistoryAdapter
import com.knotworking.numbers.converter.history.HistoryItemActions
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created on 16.02.18.
 */
class ConverterViewModel(private val fragment: ConverterFragment): HistoryItemActions {
    var conversionItem = ConversionItem(0, 0, 0f,1, 0f)

    //TODO inject singletons
    val databaseHelper: DatabaseHelper = DatabaseHelperImpl(fragment.context)

    val historyAdapter = HistoryAdapter(this)

    override fun onItemClick(item: ConversionItem) {
        fragment.loadHistoryItem(item)
    }

    override fun onItemDeleteClick(item: ConversionItem) {
        databaseHelper.deleteConversionHistoryItem(item.id)
    }

}