package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Jan on 05.12.2016.
 */

public class SavedSharedPrefrencesModulWeight {
    private static int height;
    private static float weightgoal;
    private static int age;
    private static int gender;


    static void setSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        height = Integer.parseInt(sharedPreferences.getString("height", String.valueOf(180)));
        weightgoal = Integer.parseInt(sharedPreferences.getString("weightgoal", String.valueOf(80)));
        age = Integer.parseInt(sharedPreferences.getString("age", String.valueOf(18)));
        gender = Integer.parseInt(sharedPreferences.getString("gender", String.valueOf(1)));
    }


    public static int getHeight() {
        return height;
    }

    public static float getWeightGoal() {
        return weightgoal;
    }

    static int getAge() {
        return age;
    }

    static void setAge(int age) {
        SavedSharedPrefrencesModulWeight.age = age;
    }

    static int getGender() {
        return gender;
    }

    static void setGender(int gender) {
        SavedSharedPrefrencesModulWeight.gender = gender;
    }
}
