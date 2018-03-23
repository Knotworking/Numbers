package com.knotworking.numbers.converter.history

import android.content.Context
import com.knotworking.numbers.Utils
import com.knotworking.numbers.converter.ConversionItem

/**
 * Created on 31.12.17.
 */
class HistoryItemViewModel(val item: ConversionItem) {

    fun getInputDisplayText(context: Context): String {
        return getDisplayText(context, item.inputValue, item.inputUnitCode)
    }

    fun getOutputDisplayText(context: Context): String {
        return getDisplayText(context, item.outputValue, item.outputUnitCode)
    }

    private fun getDisplayText(context: Context, value: Float, unitCode: Int): String {
        val displayValue = Utils.round(value).toString()
        val unit = Utils.getUnitSymbol(context, item.unitType, unitCode)
        return displayValue + unit
    }

}