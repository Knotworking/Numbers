package com.knotworking.numbers.counter

/**
 * Created by BRL on 20/04/17.
 */

interface CounterActions {
    fun itemLongClick(item: CounterItem): Boolean

    fun incrementCount(id: Int)

    fun decrementCount(id: Int)
}
