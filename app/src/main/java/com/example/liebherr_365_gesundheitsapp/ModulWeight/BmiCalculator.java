package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;

class BmiCalculator {
    private static float minRecBmi;
    private static float maxRecBmi;
    private static float minRecWeight;
    private static float maxRecWeight;
    private static float averageRecWeight;
    private static float height;

    static float calculateBmi(Context context) {
        int weight;

        DataSourceData dataSourceData = new DataSourceData(context);
        dataSourceData.open();

        // getCurrentWeight
        weight = (int) dataSourceData.getLatestEntry("ModulWeight");

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

    // function setRecBmi
    static void setRecBmi() {
        int age = SavedSharedPrefrencesModulWeight.getAge();
        Log.d("AGE", String.valueOf(age));

        // Quelle http://www.bmi-tabellen.de/
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

        // female bmi 1 unit smaller!
        if (SavedSharedPrefrencesModulWeight.getGender() == 0) {
            minRecBmi--;
            maxRecBmi--;
        }

        Log.d("*****MIN REC BMI *****", String.valueOf(minRecBmi));
        Log.d("****MAXRECBMI****", String.valueOf(maxRecBmi));
    }

    static float getMinRecBmi() {
        return minRecBmi;
    }

    static float getMaxRecBmi() {
        return maxRecBmi;
    }

    static float getAverageRecWeight() {
        return averageRecWeight;
    }

    private static void calculateRecWeight() {
        minRecWeight = Math.round(getMinRecBmi() * height * height);
        maxRecWeight = Math.round(getMaxRecBmi() * height * height);
        averageRecWeight = ((minRecWeight + maxRecWeight) / 2);

        Log.d("***** Min WEight***", Float.toString(minRecWeight));
        Log.d("***** Max WEight***", Float.toString(maxRecWeight));
        Log.d("***** Average WEight***", Float.toString(averageRecWeight));

    }

    static float getMinRecWeight() {
        return minRecWeight;
    }

    static float getMaxRecWeight() {
        return maxRecWeight;
    }

}
