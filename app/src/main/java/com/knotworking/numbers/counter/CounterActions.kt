package com.knotworking.numbers.counter

/**
 * Created by BRL on 20/04/17.
 */

interface CounterActions {
    fun itemLongClick(id: Int): Boolean

    fun nameLongClick(id: Int, name: String): Boolean

    fun incrementCount(id: Int)

    fun decrementCount(id: Int)
}
