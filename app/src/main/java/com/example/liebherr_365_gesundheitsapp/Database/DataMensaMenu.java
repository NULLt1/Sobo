package com.example.liebherr_365_gesundheitsapp.Database;

import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by Jan on 27.04.2017.
 */

public class DataMensaMenu {
    private long id;
    private String date;
    private String day;
    private String price;
    private String header;
    private String menu;
    private int weekOfTheYear;
    private static final String LOG_TAG = DataMensaMenu.class.getSimpleName();

    //Constructor for Menu
    public DataMensaMenu(long id, String date, String day, int weekOfTheYear, String header, String price, String menu) {

        this.id = id;
        this.date = date;
        this.day = day;
        this.weekOfTheYear = weekOfTheYear;
        this.price = price;
        this.header = header;
        this.menu = menu;
    }

    //Constructor for additional Menu
    public DataMensaMenu(long id, String price, String menu) {
        this.price = price;
        this.menu = menu;
    }


    public String getDate() {
        String formattedDate = date;
        Log.d(LOG_TAG, date);
        //formats the Date to german date notation
        try {
            formattedDate = new SimpleDateFormat("dd.MM.yy").format(new SimpleDateFormat("yyyy.MM.dd").parse(date));
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
        }
        return formattedDate;
    }

    public long getId() {
        return id;
    }

    public String getPrice() {
        return price + "€";
    }

    public String getHeader() {
        return header;
    }

    public String getMenu() {
        return menu;
    }

    public int getWeekOfTheYear() {
        return weekOfTheYear;
    }

    public String getDay() {
        return day;
    }

    public String toString() {
        return id + " " + date + " " + day + " " + weekOfTheYear + " " + header + " " + price + " " + menu;
    }


}

