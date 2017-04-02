package com.example.brl.unitconverter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

import kotlinx.android.synthetic.main.fragment_converter.*

import com.example.brl.unitconverter.Constants.MASS_G
import com.example.brl.unitconverter.Constants.MASS_OZ
import com.example.brl.unitconverter.Constants.TEMP_C
import com.example.brl.unitconverter.Constants.TEMP_F
import com.example.brl.unitconverter.Constants.TYPE_CURRENCY
import com.example.brl.unitconverter.Constants.TYPE_DISTANCE
import com.example.brl.unitconverter.Constants.TYPE_MASS
import com.example.brl.unitconverter.Constants.TYPE_TEMPERATURE

class ConverterFragment : Fragment(), AdapterView.OnItemSelectedListener {

//    internal var typeSpinner: Spinner? = null
//    internal var inputSpinner: Spinner? = null
//    internal var outputSpinner: Spinner? = null
    internal var typeAdapter: ArrayAdapter<CharSequence>? = null
    internal var massAdapter: ArrayAdapter<CharSequence>? = null
    internal var temperatureAdapter: ArrayAdapter<CharSequence>? = null
    internal var distanceAdapter: ArrayAdapter<CharSequence>? = null
    internal var currencyAdapter: ArrayAdapter<CharSequence>? = null

//    internal var inputEditText: EditText? = null
//    internal var outputEditText: EditText? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAdapters()

        setupSpinner(fragment_converter_type_spinner, typeAdapter)
        setupSpinner(fragment_converter_input_spinner, massAdapter)
        setupSpinner(fragment_converter_output_spinner, massAdapter)
        setupTextWatchers()
    }


    private fun setupTextWatchers() {
        fragment_converter_input_editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (fragment_converter_input_editText.hasFocus()) {
                    parametersChanged(true)
                }
            }
        })
        fragment_converter_output_editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (fragment_converter_output_editText.hasFocus()) {
                    parametersChanged(false)
                }
            }
        })
    }

    private fun setupAdapters() {
        typeAdapter = ArrayAdapter.createFromResource(context, R.array.type_array, android.R.layout.simple_spinner_item)
        typeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        massAdapter = ArrayAdapter.createFromResource(context, R.array.mass_array, android.R.layout.simple_spinner_item)
        massAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        temperatureAdapter = ArrayAdapter.createFromResource(context, R.array.temperature_array, android.R.layout.simple_spinner_item)
        temperatureAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        distanceAdapter = ArrayAdapter.createFromResource(context, R.array.distance_array, android.R.layout.simple_spinner_item)
        distanceAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyAdapter = ArrayAdapter.createFromResource(context, R.array.currency_array, android.R.layout.simple_spinner_item)
        currencyAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.fragment_converter_type_spinner -> handleTypeSelected(position)
            R.id.fragment_converter_input_spinner -> parametersChanged(true)
            R.id.fragment_converter_output_spinner -> parametersChanged(false)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private fun setupSpinner(spinner: Spinner, adapter: ArrayAdapter<CharSequence>?) {
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    private fun handleTypeSelected(position: Int) {
        when (position) {
            TYPE_MASS -> {
                fragment_converter_input_spinner.adapter = massAdapter
                fragment_converter_input_spinner.setSelection(MASS_G)
                fragment_converter_output_spinner.adapter = massAdapter
                fragment_converter_output_spinner.setSelection(MASS_OZ)
            }
            TYPE_TEMPERATURE -> {
                fragment_converter_input_spinner.adapter = temperatureAdapter
                fragment_converter_input_spinner.setSelection(TEMP_C)
                fragment_converter_output_spinner.adapter = temperatureAdapter
                fragment_converter_output_spinner.setSelection(TEMP_F)
            }
            TYPE_DISTANCE -> {
            }
            TYPE_CURRENCY -> {
            }
        }
    }

    private fun parametersChanged(inputChanged: Boolean) {
        when (fragment_converter_type_spinner.selectedItemPosition) {
            TYPE_MASS -> if (inputChanged) {
                val input = Utils.toGrams(fragment_converter_input_spinner.selectedItemPosition, Utils.getFloatFromString(fragment_converter_input_editText.text.toString()))
                val output = Utils.fromGrams(fragment_converter_output_spinner.selectedItemPosition, input)
                fragment_converter_output_editText.setText(output.toString())
            } else {
                val input = Utils.toGrams(fragment_converter_output_spinner.selectedItemPosition, Utils.getFloatFromString(fragment_converter_output_editText.text.toString()))
                val output = Utils.fromGrams(fragment_converter_input_spinner.selectedItemPosition, input)
                fragment_converter_input_editText.setText(output.toString())
            }
            TYPE_TEMPERATURE -> {
            }
            TYPE_DISTANCE -> {
            }
            TYPE_CURRENCY -> {
            }
        }
    }
}
