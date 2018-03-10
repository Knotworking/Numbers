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
    fun setupTextChangedListener(editText: EditText, listener: TextWatcher) {
        editText.addTextChangedListener(listener)
        Log.i("EditTextBindingAdapters", editText.tag.toString() + " watcher set")
    }

    @JvmStatic
    @BindingAdapter(value = ["value" ,"watcher"], requireAll = true)
    fun setText(editText: EditText, value: Float, watcher: TextWatcher) {
        val text = Utils.round(value).toString()
        Log.i("EditTextBindingAdapters", editText.tag.toString() + " set silently to: $text")
        editText.setTextSilently(text, watcher)
    }

}