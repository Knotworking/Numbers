package com.knotworking.numbers.counter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.knotworking.numbers.R;
import com.knotworking.numbers.database.DatabaseHelper;
import com.knotworking.numbers.database.DatabaseHelperImpl;

/**
 * Created by BRL on 01/05/17.
 */

public class CreateItemDialog extends DialogFragment {
    private DatabaseHelper databaseHelper;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_create_counter_item, null);
        final EditText editText = (EditText)layout.findViewById(R.id.dialog_create_counter_item_editText);

        databaseHelper = new DatabaseHelperImpl(getContext());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_item_dialog_title)
                .setView(layout)
                .setPositiveButton(R.string.new_item_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.addCounterEntry(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.new_item_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return dialogBuilder.create();
    }
}
