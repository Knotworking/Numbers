package com.knotworking.numbers.converter.history

import com.knotworking.numbers.converter.ConversionItem

/**
 * Created on 31.12.17.
 */
interface HistoryItemActions {

    fun onItemClick(item: ConversionItem)

    fun onItemDeleteClick(item: ConversionItem): Boolean

}