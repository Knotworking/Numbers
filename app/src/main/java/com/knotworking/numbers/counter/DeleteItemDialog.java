package com.knotworking.numbers.counter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.knotworking.numbers.R;
import com.knotworking.numbers.database.DatabaseHelper;
import com.knotworking.numbers.database.DatabaseHelperImpl;

/**
 * Created by BRL on 01/05/17.
 */

public class DeleteItemDialog extends DialogFragment {

    private static final String ARG_ID = "id";

    private DatabaseHelper databaseHelper;

    public static DeleteItemDialog newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        DeleteItemDialog dialogFragment = new DeleteItemDialog();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelperImpl(getContext());
        Bundle arguments = getArguments();
        final int id = arguments.getInt(ARG_ID);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_item_dialog_title)
                .setMessage(R.string.delete_item_dialog_message)
                .setPositiveButton(R.string.delete_item_dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteCounterItem(id);
            }

        }).setNegativeButton(R.string.delete_item_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return dialogBuilder.create();
    }
}
