package com.knotworking.numbers.databinding

import android.databinding.BindingAdapter
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.knotworking.numbers.Utils
import com.knotworking.numbers.setTextSilently

/**
 * Created on 26.02.18.
 */
object EditTextBindingAdapters {

    @JvmStatic
    @BindingAdapter("listener")
    fun setupEditTextListener(editText: EditText, listener: TextWatcher) {
        editText.addTextChangedListener(listener)
    }

    @JvmStatic
    @BindingAdapter(value = ["value", "watcher"], requireAll = true)
    fun setText(editText: EditText, value: Float, watcher: TextWatcher) {
        val newValue = Utils.round(value)
        val oldValue = Utils.getFloatFromString(editText.text.toString())
        if (newValue != oldValue) {
            Log.i("EditTextBindingAdapters", editText.tag.toString() + " set silently to: $newValue")
            editText.setTextSilently(newValue.toString(), watcher)
        }
    }
}