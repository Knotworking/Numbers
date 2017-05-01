package com.knotworking.numbers.counter;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by BRL on 22/04/17.
 */

public class CounterDiffCallback extends DiffUtil.Callback {

    private List<CounterItem> oldList;
    private List<CounterItem> newList;

    public CounterDiffCallback(List<CounterItem> oldList, List<CounterItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CounterItem oldItem = oldList.get(oldItemPosition);
        CounterItem newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
