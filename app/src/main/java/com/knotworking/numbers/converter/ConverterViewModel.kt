package com.knotworking.numbers.converter

import com.knotworking.numbers.converter.history.HistoryAdapter
import com.knotworking.numbers.converter.history.HistoryItem
import com.knotworking.numbers.converter.history.HistoryItemActions
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created on 16.02.18.
 */
class ConverterViewModel(val fragment: ConverterFragment): HistoryItemActions {
    //TODO inject singletons
    val databaseHelper: DatabaseHelper = DatabaseHelperImpl(fragment.context)

    val historyAdapter = HistoryAdapter(this)

    override fun onItemClick(item: HistoryItem) {
        fragment.loadHistoryItem(item)
    }

    override fun onItemDeleteClick(item: HistoryItem) {
        databaseHelper.deleteConversionHistoryItem(item.id)
    }

}