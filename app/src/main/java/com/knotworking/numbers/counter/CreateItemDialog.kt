package com.knotworking.numbers.counter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.knotworking.numbers.R
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created by BRL on 01/05/17.
 */

class CreateItemDialog : DialogFragment() {

    //TODO inject
    private lateinit var databaseHelper: DatabaseHelper

    private var rootView: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.dialog_edit_text, null)
        rootView = layout
        val editText = layout.findViewById<View>(R.id.dialog_item_label_editText) as EditText
        editText.setHint(R.string.new_item_dialog_hint)

        databaseHelper = DatabaseHelperImpl(context!!)

        val dialogBuilder = AlertDialog.Builder(activity!!)
                .setTitle(R.string.new_item_dialog_title)
                .setView(layout)
                .setPositiveButton(R.string.new_item_dialog_positive_button) { dialog, which -> databaseHelper.addCounterEntry(editText.text.toString()) }
                .setNegativeButton(R.string.dialog_button_cancel) { dialog, which -> dialog.dismiss() }

        val dialog = dialogBuilder.create()

        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }

    override fun onDismiss(dialogInterface: DialogInterface?) {
        super.onDismiss(dialogInterface)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}
