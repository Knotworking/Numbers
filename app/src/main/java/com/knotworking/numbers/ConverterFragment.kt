package com.knotworking.numbers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.knotworking.numbers.Constants.*
import kotlinx.android.synthetic.main.fragment_converter.*

class ConverterFragment : Fragment(), AdapterView.OnItemSelectedListener {

    internal var typeAdapter: ArrayAdapter<CharSequence>? = null
    internal var massAdapter: ArrayAdapter<CharSequence>? = null
    internal var temperatureAdapter: ArrayAdapter<CharSequence>? = null
    internal var distanceAdapter: ArrayAdapter<CharSequence>? = null
    internal var currencyAdapter: ArrayAdapter<CharSequence>? = null

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
            R.id.fragment_converter_output_spinner -> parametersChanged(true)
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
                fragment_converter_input_spinner.adapter = distanceAdapter
                fragment_converter_input_spinner.setSelection(DIST_MI)
                fragment_converter_output_spinner.adapter = distanceAdapter
                fragment_converter_output_spinner.setSelection(DIST_KM)
            }
            TYPE_CURRENCY -> {
            }
        }
    }

    private fun parametersChanged(updateOutput: Boolean) {
        val inputUnitCode = if (updateOutput) fragment_converter_input_spinner.selectedItemPosition else fragment_converter_output_spinner.selectedItemPosition
        val outputUnitCode = if (updateOutput) fragment_converter_output_spinner.selectedItemPosition else fragment_converter_input_spinner.selectedItemPosition
        val inputEditText = if (updateOutput) fragment_converter_input_editText else fragment_converter_output_editText
        val outputEditText = if (updateOutput) fragment_converter_output_editText else fragment_converter_input_editText
        var input = Utils.getFloatFromString(inputEditText.text.toString())
        var output = 0f

        when (fragment_converter_type_spinner.selectedItemPosition) {
            TYPE_MASS -> {
                input = Utils.toGrams(inputUnitCode, input)
                output = Utils.fromGrams(outputUnitCode, input)
            }
            TYPE_TEMPERATURE -> {
                output = if (inputUnitCode == TEMP_C) Utils.toFahrenheit(input) else Utils.toCelsius(input)
            }
            TYPE_DISTANCE -> {
                input = Utils.toMetres(inputUnitCode, input)
                output = Utils.fromMetres(outputUnitCode, input)
            }
            TYPE_CURRENCY -> {
            }
        }

        outputEditText.setText(Utils.round(output).toString())
    }
}
