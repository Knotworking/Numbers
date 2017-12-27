package com.knotworking.numbers.api

import android.content.Context
import android.util.Log
import com.knotworking.numbers.database.DatabaseHelper
import com.knotworking.numbers.database.DatabaseHelperImpl

/**
 * Created on 04.12.17.
 */
class CurrencyApi(context: Context) {
    private val TAG = CurrencyApi::class.java.simpleName

    private val service: CurrencyService = RestClient.createService(CurrencyService::class.java)

    private val databaseHelper: DatabaseHelper = DatabaseHelperImpl(context)

    fun getExchangeRates() {
        service.getLatestExchangeRates().enqueue(object : retrofit2.Callback<ExchangeRatesResponse> {
            override fun onFailure(call: retrofit2.Call<ExchangeRatesResponse>, t: Throwable) {
                Log.e(TAG, t.message)
            }

            override fun onResponse(call: retrofit2.Call<ExchangeRatesResponse>, response: retrofit2.Response<ExchangeRatesResponse>) {
                response.body()?.let {
                    it.rates = it.rates.filterKeys {
                        when (it) {
                            "USD", "CAD", "GBP", "EUR" -> true
                            else -> false
                        }
                    }

                    Log.i(TAG, it.rates.toString())

                    databaseHelper.saveExchangeRates(it.rates)
                }
            }

        })
    }
}