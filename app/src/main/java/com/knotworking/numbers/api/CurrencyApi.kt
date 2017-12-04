package com.knotworking.numbers.api

import android.util.Log

/**
 * Created on 04.12.17.
 */
class CurrencyApi {
    private val TAG = CurrencyApi::class.java.simpleName

    private val service: CurrencyService = RestClient.createService(CurrencyService::class.java)

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

                    //TODO store values in database
                    Log.i(TAG, it.rates.toString())
                }
            }

        })
    }
}