package com.example.liebherr_365_gesundheitsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Jan on 21.11.2016.
 */

public class BmiCalculator {
    private static float minRecBmi;
    private static float maxRecBmi;
    private static float bmi;

    public static float calculateBmi(Context context, float weight) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        float height = Float.parseFloat(sharedPreferences.getString("height", "180"));

        height /= 100.0;
        float bmi = weight / height / height;

        return bmi;
    }

    public static void setRecBmi(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int age = Integer.parseInt(sharedPreferences.getString("age", "19"));

        if (age < 25) {
            minRecBmi = 19;
            maxRecBmi = 24;
        } else if (age < 35) {
            minRecBmi = 20;
            maxRecBmi = 25;
        } else if (age < 45) {
            minRecBmi = 21;
            maxRecBmi = 26;
        } else if (age < 55) {
            minRecBmi = 22;
            maxRecBmi = 27;
        } else if (age < 66) {
            minRecBmi = 23;
            maxRecBmi = 28;
        } else if (age > 65) {
            minRecBmi = 24;
            maxRecBmi = 29;
        }
        Log.d("*****MIN REC BMI *****", String.valueOf(minRecBmi));
        Log.d("****MAXRECBMI****", String.valueOf(maxRecBmi));
    }

    public static float getMinRecBmi() {
        return minRecBmi;
    }

    public static float getMaxRecBmi() {
        return maxRecBmi;
    }
}
