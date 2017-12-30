package com.knotworking.numbers.counter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knotworking.numbers.R
import com.knotworking.numbers.databinding.CounterItemBinding

/**
 * Created by BRL on 04/04/17.
 */

class CounterAdapter(context: Context) : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    private var data: List<CounterItem> = emptyList()
    private val counterActions: CounterActionsImpl = CounterActionsImpl(context)

    fun setData(counterItems: List<CounterItem>?) {
        var counterItems = counterItems
        if (counterItems == null) {
            counterItems = emptyList()
        }

        val diffResult = DiffUtil.calculateDiff(CounterDiffCallback(this.data, counterItems))
        this.data = counterItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterAdapter.ViewHolder {
        val counterItemBinding = DataBindingUtil.inflate<CounterItemBinding>(LayoutInflater.from(parent.context), R.layout.counter_item, parent, false)
        return ViewHolder(counterItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindCounterItem(item, counterActions)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLong()
    }


    class ViewHolder(private var itemBinding: CounterItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bindCounterItem(item: CounterItem, actions: CounterActions) {
            itemBinding.item = item
            itemBinding.actions = actions
            itemBinding.executePendingBindings()
        }
    }

}
