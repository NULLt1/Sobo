package com.example.liebherr_365_gesundheitsapp.ModulCoffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.liebherr_365_gesundheitsapp.ModulWeight.SavedSharedPrefrencesModulWeight;


public class SavedSharedPrefrencesModulCoffee {
    private static int cups;

    public static int getCups() {
        return cups;
    }

    public static void setCups(int valueofcups) {
        cups = valueofcups;
    }


    static void setSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        cups = Integer.parseInt(String.valueOf(sharedPreferences.getInt("cups", 0)));
    }
}
