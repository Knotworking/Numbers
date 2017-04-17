package com.knotworking.numbers.counter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotworking.numbers.R;
import com.knotworking.numbers.databinding.CounterItemBinding;

import java.util.List;

/**
 * Created by BRL on 04/04/17.
 */

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.ViewHolder> {

    private List<CounterItem> data;

    public CounterAdapter() {
    }

    public void setData(List<CounterItem> counterItems) {
        data = counterItems;
    }

    @Override
    public CounterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CounterItemBinding counterItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.counter_item, parent, false);
        return new ViewHolder(counterItemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CounterItem item = data.get(position);
        holder.bindCounterItem(item);
    }

    @Override
    public int getItemCount() {
        return data != null? data.size() : 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public CounterItemBinding itemBinding;

        public ViewHolder(CounterItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            itemBinding.getRoot().setOnClickListener(this);
        }

        public void bindCounterItem(CounterItem item) {
            itemBinding.setItem(item);
            itemBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            //do stuff
        }
    }

}
