package com.knotworking.numbers.converter

import android.database.Cursor
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knotworking.numbers.R
import com.knotworking.numbers.Utils
import com.knotworking.numbers.converter.UnitCode.TEMP_C
import com.knotworking.numbers.converter.UnitCode.TYPE_CURRENCY
import com.knotworking.numbers.converter.UnitCode.TYPE_DISTANCE
import com.knotworking.numbers.converter.UnitCode.TYPE_MASS
import com.knotworking.numbers.converter.UnitCode.TYPE_TEMPERATURE
import com.knotworking.numbers.database.DatabaseContract
import com.knotworking.numbers.databinding.FragmentConverterBinding
import kotlinx.android.synthetic.main.fragment_converter.*

class ConverterFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private val EXCHANGE_RATE_LOADER = 10

    private lateinit var binding: FragmentConverterBinding

    private var exchangeRates: Map<String, Float> = emptyMap()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_converter, container, false)
        binding.viewModel = ConverterViewModel(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupSaveButton()

        loaderManager.initLoader(EXCHANGE_RATE_LOADER, null, this)
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
                output = if (inputUnitCode == outputUnitCode) {
                    input
                } else {
                    if (inputUnitCode == TEMP_C) Utils.toFahrenheit(input) else Utils.toCelsius(input)
                }
            }
            TYPE_DISTANCE -> {
                input = Utils.toMetres(inputUnitCode, input)
                output = Utils.fromMetres(outputUnitCode, input)
            }
            TYPE_CURRENCY -> {
                input = Utils.toUsd(inputUnitCode, input, exchangeRates)
                output = Utils.fromUsd(outputUnitCode, input, exchangeRates)
            }
        }

        outputEditText.setText(Utils.round(output).toString())
    }

    private fun setupSaveButton() {
        fragment_converter_save_button.setOnClickListener {
            saveCurrentConversion()
        }
    }

    private fun saveCurrentConversion() {
        val unitType = fragment_converter_type_spinner.selectedItemPosition
        val inputType = fragment_converter_input_spinner.selectedItemPosition
        val inputValue = Utils.getFloatFromString(fragment_converter_input_editText.text.toString())
        val outputType = fragment_converter_output_spinner.selectedItemPosition
        val outputValue = Utils.getFloatFromString(fragment_converter_output_editText.text.toString())

        val historyItem = ConversionItem(unitType, inputType, inputValue, outputType, outputValue)

        Log.i("ConverterFragment", historyItem.toString())
        binding.viewModel.notifyChange()
        return

        //TODO move remaining logic to viewmodel
        binding.viewModel.databaseHelper.addConversionHistoryItem(historyItem)
    }

    fun loadHistoryItem(item: ConversionItem) {
        binding.viewModel.setConversionItem(item)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = DatabaseContract.ExchangeRates.CONTENT_URI
        val projection = arrayOf(DatabaseContract.ExchangeRates.COL_CURRENCY,
                DatabaseContract.ExchangeRates.COL_RATE)
        return CursorLoader(context, uri, projection, null, null, null)

    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        data?.let {
            binding.viewModel.exchangeRates = ExchangeRateCursorConverter.getData(it)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        binding.viewModel.exchangeRates = emptyMap()
    }
}
