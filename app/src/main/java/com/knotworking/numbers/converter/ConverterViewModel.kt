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
import com.knotworking.numbers.converter.history.HistoryAdapter
import com.knotworking.numbers.converter.history.HistoryItemActions
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created on 16.02.18.
 */

//TODO issue, go from currency history item to temp history item

class ConverterViewModel(private val fragment: ConverterFragment) :
        BaseObservable(), HistoryItemActions, AdapterView.OnItemSelectedListener, TextWatcher {

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

    init {
        setupAdapters()
        inputOutputAdapter = massAdapter
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

    fun setConversionItem(item: ConversionItem) {
        handleTypeSelected(item.unitType)

//        unitType = item.unitType
//        inputUnitCode = item.inputUnitCode
//        inputValue = item.inputValue
//        outputUnitCode = item.outputUnitCode
//        outputValue = item.outputValue

        unitType.set(item.unitType)
        inputUnitCode.set(item.inputUnitCode)
        inputValue.set(item.inputValue)
        outputUnitCode.set(item.outputUnitCode)
        outputValue.set(item.outputValue)

        //notifyChange()
    }

    override fun onItemClick(item: ConversionItem) {
        fragment.loadHistoryItem(item)
    }

    override fun onItemDeleteClick(item: ConversionItem) {
        databaseHelper.deleteConversionHistoryItem(item.id)
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

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