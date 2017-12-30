package com.knotworking.numbers.counter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText

import com.knotworking.numbers.R
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created by BRL on 04/05/17.
 */

class EditItemDialog : DialogFragment() {

    //TODO inject
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        databaseHelper = DatabaseHelperImpl(context)
        val arguments = arguments
        val id = arguments.getInt(ARG_ID)
        val currentName = arguments.getString(ARG_NAME)

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.dialog_edit_text, null)
        val editText = layout.findViewById<View>(R.id.dialog_create_counter_item_editText) as EditText
        editText.setHint(R.string.new_item_dialog_hint)
        editText.setText(currentName)

        val dialogBuilder = AlertDialog.Builder(activity)
                .setTitle(R.string.edit_item_dialog_title)
                .setView(layout)
                .setPositiveButton(R.string.edit_item_dialog_positive_button) { dialog, which -> databaseHelper.modifyName(id, editText.text.toString()) }.setNegativeButton(R.string.dialog_button_cancel) { dialog, which -> dialog.dismiss() }

        return dialogBuilder.create()
    }

    companion object {
        private val ARG_ID = "id"
        private val ARG_NAME = "name"

        fun newInstance(id: Int, name: String): EditItemDialog {
            val args = Bundle()
            args.putInt(ARG_ID, id)
            args.putString(ARG_NAME, name)
            val dialogFragment = EditItemDialog()
            dialogFragment.arguments = args
            return dialogFragment
        }
    }
}
