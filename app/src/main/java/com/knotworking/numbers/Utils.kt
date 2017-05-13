package com.knotworking.numbers

import com.knotworking.numbers.Constants.*

/**
 * Created by BRL on 25/03/17.
 */

object Utils {
    fun toGrams(inputUnitCode: Int, input: Float): Float {
        when (inputUnitCode) {
            MASS_KG -> return input * 1000
            MASS_G -> return input
            MASS_LBS -> return input * 453.59237f
            MASS_OZ -> return input * 28.34952f
            else -> return 0f
        }
    }

    fun fromGrams(outputUnitCode: Int, grams: Float): Float {
        when (outputUnitCode) {
            MASS_KG -> return grams / 1000
            MASS_G -> return grams
            MASS_LBS -> return grams / 453.59237f
            MASS_OZ -> return grams / 28.34952f
            else -> return 0f
        }
    }

    fun toCelsius(fahrenheit: Float): Float {
          return (fahrenheit - 32) / 1.8f
    }

    fun toFahrenheit(celsius: Float): Float {
        return celsius * 1.8f + 32
    }

    fun toMetres(inputUnitCode: Int, input: Float): Float {
        when (inputUnitCode) {
            DIST_MI -> return input * 1609.344f
            DIST_F -> return input * 0.3048f
            DIST_KM -> return input * 1000
            DIST_M -> return input
            else -> return 0f
        }
    }

    fun fromMetres(outputUnitCode: Int, metres: Float): Float {
        when (outputUnitCode) {
            DIST_MI -> return metres / 1609.344f
            DIST_F -> return metres / 0.3048f
            DIST_KM -> return metres / 1000
            DIST_M -> return metres
            else -> return 0f
        }
    }

    fun round(input: Float): Float {
        return Math.round(input * 100) / 100f
    }

    fun getFloatFromString(floatString: String): Float {
        try {
            return java.lang.Float.valueOf(floatString)!!
        } catch (e: Exception) {
            return 0f
        }

    }
}
