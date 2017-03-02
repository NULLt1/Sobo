package com.example.liebherr_365_gesundheitsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jan on 05.12.2016.
 */

public class SavedSharedPrefrences {
    private static int height;
    private static float weightgoal;
    private static int age;


    public static void setSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        height = Integer.parseInt(sharedPreferences.getString("height", String.valueOf(18)));
        weightgoal = Integer.parseInt(sharedPreferences.getString("weightgoal", String.valueOf(18)));
        age = Integer.parseInt(sharedPreferences.getString("age", String.valueOf(18)));
    }

    public static int getHeight() {
        return height;
    }

    public static float getWeightGoal() {
        return weightgoal;
    }

    public static int getAge() {
        return age;
    }

}
