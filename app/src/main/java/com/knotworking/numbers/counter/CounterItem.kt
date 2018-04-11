package com.knotworking.numbers.counter

import android.databinding.BaseObservable
import android.databinding.Bindable

class CounterItem(var id: Int,
                  @get:Bindable var name: String?,
                  @get:Bindable var count: Int) : BaseObservable() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CounterItem) return false

        val item = other as CounterItem?
        if (this.name != item!!.name) return false
        return if (this.count != item.count) false else id == item.id

    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + count
        return result
    }
}
