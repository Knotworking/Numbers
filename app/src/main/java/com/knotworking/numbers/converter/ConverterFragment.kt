package com.knotworking.numbers.converter

import android.database.Cursor
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knotworking.numbers.R
import com.knotworking.numbers.converter.history.HistoryCursorConverter
import com.knotworking.numbers.database.DatabaseContract
import com.knotworking.numbers.databinding.FragmentConverterBinding

class ConverterFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private val EXCHANGE_RATE_LOADER = 10
    private val CONVERSION_HISTORY_LOADER = 20

    private lateinit var binding: FragmentConverterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_converter, container, false)
        binding.viewModel = ConverterViewModel(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loaderManager.initLoader(CONVERSION_HISTORY_LOADER, null, this)
        loaderManager.initLoader(EXCHANGE_RATE_LOADER, null, this)
    }

    fun loadHistoryItem(item: ConversionItem) {
        binding.viewModel?.setConversionItem(item)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        when (id) {
            EXCHANGE_RATE_LOADER -> {
                val uri = DatabaseContract.ExchangeRates.CONTENT_URI
                val projection = arrayOf(DatabaseContract.ExchangeRates.COL_CURRENCY,
                        DatabaseContract.ExchangeRates.COL_RATE)
                return CursorLoader(context!!, uri, projection, null, null, null)
            }
            else -> {
                val uri = DatabaseContract.ConversionHistory.CONTENT_URI
                val projection = arrayOf(DatabaseContract.ConversionHistory.COL_ID,
                        DatabaseContract.ConversionHistory.COL_UNIT_TYPE_CODE,
                        DatabaseContract.ConversionHistory.COL_INPUT_UNIT_CODE,
                        DatabaseContract.ConversionHistory.COL_INPUT_VALUE,
                        DatabaseContract.ConversionHistory.COL_OUTPUT_UNIT_CODE,
                        DatabaseContract.ConversionHistory.COL_OUTPUT_VALUE)
                val sortOrder = DatabaseContract.ConversionHistory.COL_ID + " DESC"
                return CursorLoader(context!!, uri, projection, null, null, sortOrder)
            }
        }


    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        when (loader.id) {
            EXCHANGE_RATE_LOADER -> {
                data?.let {
                    binding.viewModel!!.exchangeRates = ExchangeRateCursorConverter.getData(it)
                }
            }
            CONVERSION_HISTORY_LOADER -> {
                data?.let {
                    binding.viewModel!!.historyAdapter.setData(HistoryCursorConverter.getData(it))
                }
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        when (loader.id) {
            EXCHANGE_RATE_LOADER -> binding.viewModel!!.exchangeRates = emptyMap()
            CONVERSION_HISTORY_LOADER -> binding.viewModel!!.historyAdapter.setData(emptyList())
        }

    }
}
