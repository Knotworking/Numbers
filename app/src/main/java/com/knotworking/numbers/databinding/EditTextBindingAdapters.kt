package com.knotworking.numbers.databinding

import android.databinding.BindingAdapter
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.knotworking.numbers.R
import com.knotworking.numbers.Utils
import com.knotworking.numbers.converter.ConverterViewModel
import com.knotworking.numbers.setTextSilently

/**
 * Created on 26.02.18.
 */
object EditTextBindingAdapters {

    private val OTHER_EDIT_TEXT = -1
    private val INPUT_EDIT_TEXT = 0
    private val OUTPUT_EDIT_TEXT = 1

    @JvmStatic
    @BindingAdapter("viewModel")
    fun setupEditText(editText: EditText, viewModel: ConverterViewModel) {

        val editTextId = when (editText.id) {
            R.id.fragment_converter_input_editText -> INPUT_EDIT_TEXT
            R.id.fragment_converter_output_editText -> OUTPUT_EDIT_TEXT
            else -> OTHER_EDIT_TEXT
        }

        setupFocusCHangedListener(editText, editTextId, viewModel)

        setupTextWatcher(editTextId, editText, viewModel)
    }

    private fun setupTextWatcher(editTextId: Int, editText: EditText, viewModel: ConverterViewModel) {
        when (editTextId) {
            INPUT_EDIT_TEXT -> {
                editText.addTextChangedListener(viewModel.inputEditTextWatcher)
            }
            OUTPUT_EDIT_TEXT -> {
                editText.addTextChangedListener(viewModel.outputEditTextWatcher)
            }
        }
    }

    private fun setupFocusCHangedListener(editText: EditText, editTextId: Int, viewModel: ConverterViewModel) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                when (editTextId) {
                    INPUT_EDIT_TEXT -> {
                        viewModel.inputValue.set(Utils.getFloatFromString(editText.text.toString()))
                    }
                    OUTPUT_EDIT_TEXT -> {
                        viewModel.outputValue.set(Utils.getFloatFromString(editText.text.toString()))
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["value", "watcher"], requireAll = true)
    fun setText(editText: EditText, value: Float, watcher: TextWatcher) {
        val text = Utils.round(value).toString()
        Log.i("EditTextBindingAdapters", editText.tag.toString() + " set silently to: $text")
        editText.setTextSilently(text, watcher)
    }
}