package com.knotworking.numbers.counter;

import android.content.Context;
import android.net.Uri;

import com.knotworking.numbers.database.CounterContract;

/**
 * Created by BRL on 20/04/17.
 */

public class CounterActionsImpl implements CounterActions {

    private Context context;

    public CounterActionsImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean deleteItem(int id) {
        Uri uri = Uri.withAppendedPath(CounterContract.Counters.CONTENT_URI, Integer.toString(id));
        context.getContentResolver().delete(uri, null, null);
        return true;
    }

}
