package com.example.liebherr_365_gesundheitsapp;

import android.util.Log;

import java.sql.Date;

public class Weightdata {
    private float weight;
    private String date;
    private float bmi;

    public Weightdata(float weight, String date, float bmi) {
        this.weight = weight;
        this.date = date;
        this.bmi = bmi;
    }

    public float getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    public float getBmi() {
        return bmi;
    }

    public Date getDateasDate() {
        //convert datestrings to int
        int yearinteger = Integer.parseInt(date.substring(0, 4));
        int monthinteger = Integer.parseInt(date.substring(5, 7));
        int dayinteger = Integer.parseInt(date.substring(8));


        return new Date(yearinteger, monthinteger, dayinteger);
    }
}
