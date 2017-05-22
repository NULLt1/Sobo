package com.example.liebherr_365_gesundheitsapp.Database;

/**
 * Created by Jan on 21.05.2017.
 */

public class DataHealthCare {
    private long id;
    private String date;
    private String course;
    private String venue;
    private String price;
    private String status;


    public DataHealthCare(long id, String date, String course, String venue, String price, String status) {
        this.id = id;
        this.date = date;
        this.course = course;
        this.venue = venue;
        this.price = price;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCourse() {
        return course;
    }

    public String getVenue() {
        return venue;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return "ID: " + getId() +
                " Date: " + getDate() +
                " Course: " + getCourse() +
                " Venue: " + getVenue() +
                " Price: " + getPrice() +
                " Status: " + getStatus();
    }
}

