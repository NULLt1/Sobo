package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SavedSharedPrefrencesModulWeight {
    private static int height;
    private static int age;
    private static int gender;
    private static boolean firstentry;


    static void setSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        height = Integer.parseInt(sharedPreferences.getString("height", String.valueOf(180)));
        age = Integer.parseInt(sharedPreferences.getString("age", String.valueOf(18)));
        gender = Integer.parseInt(sharedPreferences.getString("gender", String.valueOf(1)));
        firstentry = Boolean.parseBoolean(sharedPreferences.getString("flag", "true"));
    }

    public static int getHeight() {
        return height;
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

    static void setFirstentry(boolean flag) {
        SavedSharedPrefrencesModulWeight.firstentry = flag;
    }

    static boolean getFirstentry() {
        return firstentry;
    }
}
