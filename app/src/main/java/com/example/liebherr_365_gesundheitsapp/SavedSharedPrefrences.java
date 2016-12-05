package com.example.liebherr_365_gesundheitsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jan on 05.12.2016.
 */

public class SavedSharedPrefrences {
    private static int weightgoal;
    private static int height;
    private static int age;


    public static void setSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        age = Integer.parseInt(sharedPreferences.getString("height", null));
        weightgoal = Integer.parseInt(sharedPreferences.getString("weightgoal", null));
        age = Integer.parseInt(sharedPreferences.getString("age",null));
    }

    public static int getHeight() {


        return age;
    }

    public static int getWeightGoal() {


        return weightgoal;
    }

    public static int getAge() {


        return age;
    }

}
