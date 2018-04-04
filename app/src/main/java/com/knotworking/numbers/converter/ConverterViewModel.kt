package com.knotworking.numbers.converter

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.knotworking.numbers.BR
import com.knotworking.numbers.R
import com.knotworking.numbers.Utils
import com.knotworking.numbers.converter.history.HistoryAdapter
import com.knotworking.numbers.converter.history.HistoryItemActions
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created on 16.02.18.
 */

//TODO issue, go from currency history item to temp history item

class ConverterViewModel(private val fragment: ConverterFragment) :
        BaseObservable(), HistoryItemActions, AdapterView.OnItemSelectedListener {

    lateinit var typeAdapter: ArrayAdapter<CharSequence>
    private lateinit var massAdapter: ArrayAdapter<CharSequence>
    private lateinit var temperatureAdapter: ArrayAdapter<CharSequence>
    private lateinit var distanceAdapter: ArrayAdapter<CharSequence>
    private lateinit var currencyAdapter: ArrayAdapter<CharSequence>

    //TODO observable fields or base observable?
    var unitType: ObservableField<Int> = ObservableField(UnitCode.TYPE_MASS)

    var inputUnitCode: ObservableField<Int> = ObservableField(UnitCode.MASS_KG)

    var inputValue: ObservableField<Float> = ObservableField(1.0f)

    var outputUnitCode: ObservableField<Int> = ObservableField(UnitCode.MASS_G)

    var outputValue: ObservableField<Float> = ObservableField(0f)

    val context: Context = fragment.context

    //TODO inject singletons
    val databaseHelper: DatabaseHelper = DatabaseHelperImpl(context)

    var inputOutputAdapter: ArrayAdapter<CharSequence>? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.inputOutputAdapter)
        }

    val historyAdapter = HistoryAdapter(this)

    var exchangeRates: Map<String, Float> = emptyMap()

    lateinit var inputEditTextWatcher: TextWatcher
    lateinit var outputEditTextWatcher: TextWatcher

    init {
        setupAdapters()
        inputOutputAdapter = massAdapter
        setupEditTextWatchers()
    }

    private fun setupAdapters() {
        typeAdapter = ArrayAdapter.createFromResource(context, R.array.type_array, android.R.layout.simple_spinner_item)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        massAdapter = ArrayAdapter.createFromResource(context, R.array.mass_array, android.R.layout.simple_spinner_item)
        massAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        temperatureAdapter = ArrayAdapter.createFromResource(context, R.array.temperature_array, android.R.layout.simple_spinner_item)
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        distanceAdapter = ArrayAdapter.createFromResource(context, R.array.distance_array, android.R.layout.simple_spinner_item)
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyAdapter = ArrayAdapter.createFromResource(context, R.array.currency_array, android.R.layout.simple_spinner_item)
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun setupEditTextWatchers() {
        inputEditTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = Utils.getFloatFromString(s.toString())
                inputValue.set(value)
                calculateConversion(value)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }

        outputEditTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = Utils.getFloatFromString(s.toString())
                outputValue.set(value)
                calculateConversion(value, true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }
    }

    private fun calculateConversion(changedValue: Float, outputChanged: Boolean = false) {
        Log.i("TAG", "Calculate conversion. OutputChanged: $outputChanged")
        Log.i("TAG", "Calculate conversion. input value: $changedValue")

        var input = changedValue

        val inputCode: Int = if (outputChanged) {
            outputUnitCode.get()!!
        } else {
            inputUnitCode.get()!!
        }

        val outputCode: Int = if (outputChanged) {
            inputUnitCode.get()!!
        } else {
            outputUnitCode.get()!!
        }

        var output = 0f

        when (unitType.get()) {
            UnitCode.TYPE_MASS -> {
                input = Utils.toGrams(inputCode, input)
                output = Utils.fromGrams(outputCode, input)
            }
            UnitCode.TYPE_TEMPERATURE -> {
                output = if (inputCode == outputCode) {
                    input
                } else {
                    if (inputCode == UnitCode.TEMP_C) Utils.toFahrenheit(input)
                    else Utils.toCelsius(input)
                }
            }
            UnitCode.TYPE_DISTANCE -> {
                input = Utils.toMetres(inputCode, input)
                output = Utils.fromMetres(outputCode, input)
            }
            UnitCode.TYPE_CURRENCY -> {
                input = Utils.toUsd(inputCode, input, exchangeRates)
                output = Utils.fromUsd(outputCode, input, exchangeRates)
            }
        }

        if (outputChanged) {
            inputValue.set(output)
        } else {
            outputValue.set(output)
        }
    }

    fun saveCurrentItem() {
        val conversionItem = ConversionItem(unitType.get()!!,
                inputUnitCode.get()!!,
                inputValue.get()!!,
                outputUnitCode.get()!!,
                outputValue.get()!!)

        databaseHelper.addConversionHistoryItem(conversionItem)
    }

    fun setConversionItem(item: ConversionItem) {
        handleTypeSelected(item.unitType)

        unitType.set(item.unitType)
        inputUnitCode.set(item.inputUnitCode)
        inputValue.set(item.inputValue)
        outputUnitCode.set(item.outputUnitCode)
        outputValue.set(item.outputValue)
    }

    override fun onItemClick(item: ConversionItem) {
        fragment.loadHistoryItem(item)
    }

    override fun onItemDeleteClick(item: ConversionItem) {
        databaseHelper.deleteConversionHistoryItem(item.id)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent?.id) {
            R.id.fragment_converter_type_spinner -> {
                handleTypeSelected(position)
            }
            R.id.fragment_converter_input_spinner -> {
                inputUnitCode.set(position)
                Log.i("ConverterViewModel", "Input code selected: " + (inputOutputAdapter?.getItem(position)))
            }
            R.id.fragment_converter_output_spinner -> {
                outputUnitCode.set(position)
                Log.i("ConverterViewModel", "Output code selected: " + (inputOutputAdapter?.getItem(position)))
            }
        }

        calculateConversion(inputValue.get()!!)
    }

    private fun handleTypeSelected(position: Int) {
        unitType.set(position)

        when (position) {
            UnitCode.TYPE_MASS -> {
                Log.i("ConverterViewModel", "Mass selected")
                inputOutputAdapter = massAdapter
                inputUnitCode.set(UnitCode.MASS_G)
                outputUnitCode.set(UnitCode.MASS_OZ)
            }
            UnitCode.TYPE_TEMPERATURE -> {
                Log.i("ConverterViewModel", "Temp selected")
                inputOutputAdapter = temperatureAdapter
                inputUnitCode.set(UnitCode.TEMP_C)
                outputUnitCode.set(UnitCode.TEMP_F)
            }
            UnitCode.TYPE_DISTANCE -> {
                Log.i("ConverterViewModel", "Dist selected")
                inputOutputAdapter = distanceAdapter
                inputUnitCode.set(UnitCode.DIST_MI)
                outputUnitCode.set(UnitCode.DIST_KM)
            }
            UnitCode.TYPE_CURRENCY -> {
                Log.i("ConverterViewModel", "$$$ selected")
                inputOutputAdapter = currencyAdapter
                inputUnitCode.set(UnitCode.EUR)
                outputUnitCode.set(UnitCode.USD)
            }
        }
    }

}