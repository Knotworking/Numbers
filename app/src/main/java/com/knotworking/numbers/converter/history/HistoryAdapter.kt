package com.knotworking.numbers.converter.history

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knotworking.numbers.R
import com.knotworking.numbers.converter.ConversionItem
import com.knotworking.numbers.databinding.ConversionHistoryItemBinding

/**
 * Created on 16.02.18.
 */
class HistoryAdapter(val actions: HistoryItemActions): RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>() {

    private var data: List<ConversionItem> = emptyList()

    fun setData(conversionItems: List<ConversionItem>) {
        //TODO diffutil
        data = conversionItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ConversionHistoryItemBinding>(inflater, R.layout.conversion_history_item, parent, false)
        return HistoryItemViewHolder(binding)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.binding.viewModel = HistoryItemViewModel(data[position])
        holder.binding.actions = actions
    }

    class HistoryItemViewHolder(val binding: ConversionHistoryItemBinding) : RecyclerView.ViewHolder(binding.root)
}