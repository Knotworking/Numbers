package com.knotworking.numbers;

import static com.knotworking.numbers.Constants.*;

/**
 * Created by BRL on 25/03/17.
 */

public class Utils {
    public static float toGrams(int inputUnit, float input) {
        switch (inputUnit) {
            case MASS_KG:
                return input * 1000;
            case MASS_G:
                return input;
            case MASS_LBS:
                return input * 453.59237f;
            case MASS_OZ:
                return input * 28.34952f;
            default:
                return 0;
        }
    }

    public static float fromGrams(int outputUnit, float grams) {
        switch (outputUnit) {
            case MASS_KG:
                return grams / 1000;
            case MASS_G:
                return grams;
            case MASS_LBS:
                return grams / 453.59237f;
            case MASS_OZ:
                return grams / 28.34952f;
            default:
                return 0;
        }
    }

    public static float getFloatFromString(String floatString) {
        try {
            return Float.valueOf(floatString);
        } catch (Exception e) {
            return 0;
        }
    }
}
