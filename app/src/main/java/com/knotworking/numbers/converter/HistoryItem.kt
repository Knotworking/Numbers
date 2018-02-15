package com.knotworking.numbers.converter

/**
 * Created on 31.12.17.
 */
data class HistoryItem(val unitType: Int,
                       val inputUnitCode: Int,
                       val inputValue: Float,
                       val outputUnitCode: Int,
                       val outputValue: Float)