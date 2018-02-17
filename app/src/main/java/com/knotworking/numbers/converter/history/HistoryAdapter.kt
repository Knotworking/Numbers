package com.knotworking.numbers.converter.history

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knotworking.numbers.R
import com.knotworking.numbers.converter.UnitCode
import com.knotworking.numbers.databinding.ConversionHistoryItemBinding

/**
 * Created on 16.02.18.
 */
class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>(), HistoryItemActions {

    private var data: List<HistoryItem> = dummyData()

    //TODO remove
    private fun dummyData(): List<HistoryItem> {
        val historyItem1 = HistoryItem(UnitCode.TYPE_MASS,
                UnitCode.MASS_G, 1000f,
                UnitCode.MASS_KG, 1f)
        val historyItem2 = HistoryItem(UnitCode.TYPE_TEMPERATURE,
                UnitCode.TEMP_C, 0f,
                UnitCode.TEMP_F, 32f)
        val historyItem3 = HistoryItem(UnitCode.TYPE_CURRENCY,
                UnitCode.GBP, 10.99f,
                UnitCode.USD, 19.76f)
        return listOf(historyItem1, historyItem2, historyItem3)
    }

    fun setData(historyItems: List<HistoryItem>) {
        data = historyItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ConversionHistoryItemBinding>(inflater, R.layout.conversion_history_item, parent, false)
        return HistoryItemViewHolder(binding)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.binding.item = HistoryItemViewModel(data[position])
        holder.binding.actions = this
    }

    override fun onItemClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemDeleteClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class HistoryItemViewHolder(val binding: ConversionHistoryItemBinding) : RecyclerView.ViewHolder(binding.root)
}