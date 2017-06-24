package com.example.liebherr_365_gesundheitsapp.ModulDrink;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class SavedSharedPrefrencesModulDrink {
    private static int liter;

    static int getLiter() {
        return liter;
    }

    static void setLiter(int valueofcups) {
        liter = valueofcups;
    }


    static void setSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        liter = Integer.parseInt(String.valueOf(sharedPreferences.getInt("liter", 2)));
    }
}

