package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.liebherr_365_gesundheitsapp.Database.DBHelperDataSourceData;
import com.example.liebherr_365_gesundheitsapp.SavedSharedPrefrences;

/**
 * Created by Jan on 21.11.2016.
 */

public class BmiCalculator {
    private static DBHelperDataSourceData dataSourceData;
    private static float minRecBmi;
    private static float maxRecBmi;
    private static float minRecWeight;
    private static float maxRecWeight;
    private static float bmi;
    private static float height;

    public static float calculateBmi(Context context) {
        int weight;

        dataSourceData = new DBHelperDataSourceData(context);
        dataSourceData.open();

        // getCurrentWeight
        weight = dataSourceData.getLatestEntry();

        dataSourceData.close();

        if (weight == 0) {
            weight = 80;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        height = Float.parseFloat(sharedPreferences.getString("height", "180"));

        height /= 100.0;
        float bmi = weight / height / height;
        calculateRecWeight();
        return bmi;
    }

    public static void setRecBmi() {
        int age = SavedSharedPrefrences.getAge();

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

    public static void calculateRecWeight() {
        minRecWeight = Math.round(getMinRecBmi() * height * height);
        maxRecWeight = Math.round(getMaxRecBmi() * height * height);

        Log.d("***** Min WEight***", Float.toString(minRecWeight));
        Log.d("***** Max WEight***", Float.toString(maxRecWeight));
    }

    public static float getMinRecWeight() {
        return minRecWeight;
    }

    public static float getMaxRecWeight() {
        return maxRecWeight;
    }

}
