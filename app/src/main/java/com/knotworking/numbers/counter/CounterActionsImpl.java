package com.knotworking.numbers.counter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.knotworking.numbers.MainActivity;
import com.knotworking.numbers.database.DatabaseHelper;
import com.knotworking.numbers.database.DatabaseHelperImpl;

/**
 * Created by BRL on 20/04/17.
 */

public class CounterActionsImpl implements CounterActions {
    private static final String DELETE_DIALOG = "delete_dialog";

    private Context context;
    private DatabaseHelper databaseHelper;

    public CounterActionsImpl(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelperImpl(context);
    }

    @Override
    public boolean itemLongClick(int id) {
        showDeleteItemDialog(id);
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

    private void showDeleteItemDialog(int id) {
        MainActivity activity = (MainActivity)context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(DELETE_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DeleteItemDialog dialog = DeleteItemDialog.newInstance(id);
        dialog.show(ft, DELETE_DIALOG);
    }

}
