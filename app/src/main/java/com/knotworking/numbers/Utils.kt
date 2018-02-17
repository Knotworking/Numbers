package com.knotworking.numbers

import android.content.Context
import com.knotworking.numbers.converter.UnitCode
import com.knotworking.numbers.converter.UnitCode.CAD
import com.knotworking.numbers.converter.UnitCode.DIST_F
import com.knotworking.numbers.converter.UnitCode.DIST_KM
import com.knotworking.numbers.converter.UnitCode.DIST_M
import com.knotworking.numbers.converter.UnitCode.DIST_MI
import com.knotworking.numbers.converter.UnitCode.EUR
import com.knotworking.numbers.converter.UnitCode.GBP
import com.knotworking.numbers.converter.UnitCode.MASS_G
import com.knotworking.numbers.converter.UnitCode.MASS_KG
import com.knotworking.numbers.converter.UnitCode.MASS_LBS
import com.knotworking.numbers.converter.UnitCode.MASS_OZ
import com.knotworking.numbers.converter.UnitCode.USD

/**
 * Created by BRL on 25/03/17.
 */

object Utils {
    fun toGrams(inputUnitCode: Int, input: Float): Float {
        return when (inputUnitCode) {
            MASS_KG -> input * 1000
            MASS_G -> input
            MASS_LBS -> input * 453.59237f
            MASS_OZ -> input * 28.34952f
            else -> 0f
        }
    }

    fun fromGrams(outputUnitCode: Int, grams: Float): Float {
        return when (outputUnitCode) {
            MASS_KG -> grams / 1000
            MASS_G -> grams
            MASS_LBS -> grams / 453.59237f
            MASS_OZ -> grams / 28.34952f
            else -> 0f
        }
    }

    fun toCelsius(fahrenheit: Float): Float {
        return (fahrenheit - 32) / 1.8f
    }

    fun toFahrenheit(celsius: Float): Float {
        return celsius * 1.8f + 32
    }

    fun toMetres(inputUnitCode: Int, input: Float): Float {
        return when (inputUnitCode) {
            DIST_MI -> input * 1609.344f
            DIST_F -> input * 0.3048f
            DIST_KM -> input * 1000
            DIST_M -> input
            else -> 0f
        }
    }

    fun fromMetres(outputUnitCode: Int, metres: Float): Float {
        return when (outputUnitCode) {
            DIST_MI -> metres / 1609.344f
            DIST_F -> metres / 0.3048f
            DIST_KM -> metres / 1000
            DIST_M -> metres
            else -> 0f
        }
    }

    fun toUsd(inputUnitCode: Int, input: Float, rates: Map<String, Float>): Float {
        val exchangeRate = getExchangeRate(inputUnitCode, rates)
        return input / exchangeRate
    }

    fun fromUsd(outputUnitCode: Int, usd: Float, rates: Map<String, Float>): Float {
        val exchangeRate = getExchangeRate(outputUnitCode, rates)
        return usd * exchangeRate
    }

    private fun getExchangeRate(currencyCode: Int, rates: Map<String, Float>): Float {
        return when (currencyCode) {
            EUR -> rates.getValue(Constants.EUR)
            GBP -> rates.getValue(Constants.GBP)
            USD -> rates.getValue(Constants.USD)
            CAD -> rates.getValue(Constants.CAD)
            else -> 0f
        }
    }

    fun round(input: Float): Float {
        return Math.round(input * 100) / 100f
    }

    fun getFloatFromString(floatString: String): Float {
        return try {
            java.lang.Float.valueOf(floatString)!!
        } catch (e: Exception) {
            0f
        }

    }

    //TODO use enums
    fun getUnitSymbol(context: Context, typeCode: Int, unitCode: Int): String {
        val typeArray = when (typeCode) {
            UnitCode.TYPE_MASS -> R.array.mass_array
            UnitCode.TYPE_TEMPERATURE -> R.array.temperature_array
            UnitCode.TYPE_DISTANCE -> R.array.distance_array
            UnitCode.TYPE_CURRENCY -> R.array.currency_array
            else -> 0
        }

        val spacing = if (typeCode == UnitCode.TYPE_CURRENCY) " " else ""

        return spacing + context.resources.getStringArray(typeArray)[unitCode]
    }
}
