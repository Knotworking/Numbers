package com.knotworking.numbers.counter;

import android.content.Context;

import com.knotworking.numbers.database.DatabaseHelper;
import com.knotworking.numbers.database.DatabaseHelperImpl;

/**
 * Created by BRL on 20/04/17.
 */

public class CounterActionsImpl implements CounterActions {

    private Context context;
    private DatabaseHelper databaseHelper;

    public CounterActionsImpl(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelperImpl(context);
    }

    @Override
    public boolean deleteCounterItem(int id) {
        databaseHelper.deleteCounterItem(id);
        return true;
    }

    @Override
    public void incrementCount(int id) {
        databaseHelper.modifyCount(id, 1);
    }

    @Override
    public void decrementCount(int id) {
        databaseHelper.modifyCount(id, -1);
    }

}
