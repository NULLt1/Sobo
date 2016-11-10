package com.example.liebherr_365_gesundheitsapp;

public class weightdata {
    private float weight;
    private String date;
    private float bmi;

    public weightdata(float weight, String date, float bmi) {
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
}
