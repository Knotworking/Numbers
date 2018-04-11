package com.knotworking.numbers.counter

import android.app.Dialog
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
        val currentCount = arguments.getInt(ARG_COUNT)

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.dialog_edit_text, null)

        val nameEditText = layout.findViewById<View>(R.id.dialog_item_label_editText) as EditText
        nameEditText.setHint(R.string.new_item_dialog_hint)
        nameEditText.setText(currentName)

        val countEditText = layout.findViewById<View>(R.id.dialog_item_counter_editText) as EditText
        countEditText.visibility = View.VISIBLE
        countEditText.setText(currentCount.toString())

        val dialogBuilder = AlertDialog.Builder(activity)
                .setTitle(R.string.edit_item_dialog_title)
                .setView(layout)
                .setPositiveButton(R.string.edit_item_dialog_positive_button) { dialog, which ->
                    databaseHelper.modifyName(id, nameEditText.text.toString())
                    databaseHelper.setCount(id, countEditText.text.toString().toInt())
                    dialog.dismiss()
                }
                .setNeutralButton(R.string.dialog_button_cancel) { dialog, which ->
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_button_delete) { dialog, which ->
                    databaseHelper.deleteCounterItem(id)
                    dialog.dismiss()
                }

        return dialogBuilder.create()
    }

    companion object {
        private val ARG_ID = "id"
        private val ARG_NAME = "name"
        private val ARG_COUNT = "count"

        fun newInstance(item: CounterItem): EditItemDialog {
            val args = Bundle()
            args.putInt(ARG_ID, item.id)
            args.putString(ARG_NAME, item.name)
            args.putInt(ARG_COUNT, item.count)
            val dialogFragment = EditItemDialog()
            dialogFragment.arguments = args
            return dialogFragment
        }
    }
}
