package com.knotworking.numbers.counter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

import com.knotworking.numbers.R
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created by BRL on 01/05/17.
 */

class DeleteItemDialog : DialogFragment() {

    //TODO inject
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        databaseHelper = DatabaseHelperImpl(context)
        val arguments = arguments
        val id = arguments.getInt(ARG_ID)

        val dialogBuilder = AlertDialog.Builder(activity)
                .setTitle(R.string.delete_item_dialog_title)
                .setMessage(R.string.delete_item_dialog_message)
                .setPositiveButton(R.string.delete_item_dialog_positive_button) { dialog, which -> databaseHelper.deleteCounterItem(id) }.setNegativeButton(R.string.dialog_button_cancel) { dialog, which -> dialog.dismiss() }

        return dialogBuilder.create()
    }

    companion object {

        private val ARG_ID = "id"

        fun newInstance(id: Int): DeleteItemDialog {
            val args = Bundle()
            args.putInt(ARG_ID, id)
            val dialogFragment = DeleteItemDialog()
            dialogFragment.arguments = args
            return dialogFragment
        }
    }
}
