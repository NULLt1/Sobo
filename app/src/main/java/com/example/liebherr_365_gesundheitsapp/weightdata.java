package com.example.liebherr_365_gesundheitsapp;

public class weightdata {
    private float weight;
    private int day;
    private int month;
    private int year;

    public weightdata(float weight, int day, int month, int year) {
        this.weight = weight;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public float getWeight() {
        return weight;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}
