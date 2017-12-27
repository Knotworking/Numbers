package com.knotworking.numbers

import com.knotworking.numbers.Constants.DIST_F
import com.knotworking.numbers.Constants.DIST_KM
import com.knotworking.numbers.Constants.DIST_M
import com.knotworking.numbers.Constants.DIST_MI
import com.knotworking.numbers.Constants.MASS_G
import com.knotworking.numbers.Constants.MASS_KG
import com.knotworking.numbers.Constants.MASS_LBS
import com.knotworking.numbers.Constants.MASS_OZ

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
}
