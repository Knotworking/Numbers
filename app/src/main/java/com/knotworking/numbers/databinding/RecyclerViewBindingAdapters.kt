package com.knotworking.numbers.databinding

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.knotworking.numbers.converter.history.HistoryAdapter

/**
 * Created on 16.02.18.
 */
object RecyclerViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("adapter")
    fun setupHistoryRecyclerView(recyclerView: RecyclerView, adapter: HistoryAdapter) {
        val context = recyclerView.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

}