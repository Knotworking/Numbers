package com.knotworking.numbers.counter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import com.knotworking.numbers.MainActivity
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created by BRL on 20/04/17.
 */

class CounterActionsImpl(private val context: Context) : CounterActions {

    //TODO inject
    private val databaseHelper: DatabaseHelper

    init {
        this.databaseHelper = DatabaseHelperImpl(context)
    }

    override fun itemLongClick(id: Int): Boolean {
        showDeleteItemDialog(id)
        return true
    }

    override fun nameLongClick(id: Int, name: String): Boolean {
        showEditNameDialog(id, name)
        return true
    }

    override fun incrementCount(id: Int) {
        databaseHelper.modifyCount(id, 1)
    }

    override fun decrementCount(id: Int) {
        databaseHelper.modifyCount(id, -1)
    }

    private fun showDeleteItemDialog(id: Int) {
        val activity = context as MainActivity
        val fragmentManager = activity.supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag(DELETE_DIALOG)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        val dialog = DeleteItemDialog.newInstance(id)
        dialog.show(ft, DELETE_DIALOG)
    }

    private fun showEditNameDialog(id: Int, name: String) {
        val activity = context as MainActivity
        val fragmentManager = activity.supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag(DELETE_DIALOG)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        val dialog = EditItemDialog.newInstance(id, name)
        dialog.show(ft, EDIT_DIALOG)
    }

    companion object {
        private val DELETE_DIALOG = "delete_dialog"
        private val EDIT_DIALOG = "edit_dialog"
    }

}
