package com.knotworking.numbers.counter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knotworking.numbers.R;
import com.knotworking.numbers.database.DatabaseHelper;
import com.knotworking.numbers.databinding.CounterItemBinding;

import java.util.List;

/**
 * Created by BRL on 04/04/17.
 */

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.ViewHolder> {

    private List<CounterItem> data;
    private Context context;
    protected CounterActionsImpl counterActions;

    public CounterAdapter(Context context) {
        this.context = context;
        this.counterActions = new CounterActionsImpl(context);
    }

    public void setData(List<CounterItem> counterItems) {
        data = counterItems;
        if (data != null) {
            notifyDataSetChanged();
        }
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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
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
