package com.knotworking.numbers.counter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knotworking.numbers.R;
import com.knotworking.numbers.databinding.CounterItemBinding;

import java.util.Collections;
import java.util.List;

/**
 * Created by BRL on 04/04/17.
 */

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.ViewHolder> {

    private List<CounterItem> data;
    protected CounterActionsImpl counterActions;

    public CounterAdapter(Context context) {
        this.counterActions = new CounterActionsImpl(context);
    }

    public void setData(List<CounterItem> counterItems) {
        if (counterItems == null) {
            counterItems = Collections.emptyList();
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CounterDiffCallback(this.data, counterItems));
        this.data = counterItems;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public CounterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CounterItemBinding counterItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.counter_item, parent, false);
        return new ViewHolder(counterItemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CounterItem item = data.get(position);
        holder.bindCounterItem(item, counterActions);
    }

    @Override
    public int getItemCount() {
        return data != null? data.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CounterItemBinding itemBinding;

        public ViewHolder(CounterItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bindCounterItem(CounterItem item, CounterActions actions) {
            itemBinding.setItem(item);
            itemBinding.setActions(actions);
            itemBinding.executePendingBindings();
        }
    }

}
