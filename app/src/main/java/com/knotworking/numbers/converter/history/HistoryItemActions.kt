package com.knotworking.numbers.converter.history

/**
 * Created on 31.12.17.
 */
interface HistoryItemActions {

    fun onItemClick(item: HistoryItem)

    fun onItemDeleteClick(item: HistoryItem)

}