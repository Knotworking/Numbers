package com.knotworking.numbers.counter

import android.databinding.BaseObservable
import android.databinding.Bindable

import com.knotworking.numbers.BR

class CounterItem(var id: Int, @get:Bindable
var name: String?, private var count: Int) : BaseObservable() {

    @Bindable
    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
        notifyPropertyChanged(BR.count)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CounterItem) return false

        val item = other as CounterItem?
        if (this.name != item!!.name) return false
        return if (this.getCount() != item.getCount()) false else id == item.id

    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + count
        return result
    }
}
