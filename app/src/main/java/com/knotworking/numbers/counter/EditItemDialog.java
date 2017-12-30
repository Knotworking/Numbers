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
 * Created by BRL on 04/05/17.
 */

public class EditItemDialog extends DialogFragment{
    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";

    //TODO inject
    private DatabaseHelper databaseHelper;

    public static EditItemDialog newInstance(int id, String name) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_NAME, name);
        EditItemDialog dialogFragment = new EditItemDialog();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelperImpl(getContext());
        Bundle arguments = getArguments();
        final int id = arguments.getInt(ARG_ID);
        String currentName = arguments.getString(ARG_NAME);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_edit_text, null);
        final EditText editText = (EditText)layout.findViewById(R.id.dialog_create_counter_item_editText);
        editText.setHint(R.string.new_item_dialog_hint);
        editText.setText(currentName);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.edit_item_dialog_title)
                .setView(layout)
                .setPositiveButton(R.string.edit_item_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.modifyName(id, editText.getText().toString());
                    }

                }).setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return dialogBuilder.create();
    }
}
