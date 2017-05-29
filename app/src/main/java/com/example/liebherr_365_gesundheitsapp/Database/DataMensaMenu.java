package com.example.liebherr_365_gesundheitsapp.Database;

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


    //Constructor for Menu
    public DataMensaMenu(long id, String date, String day, int weekOfTheYear, String price, String header, String menu) {

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
        return date;
    }

    public long getId() {
        return id;
    }

    public String getPrice() {
        return price;
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

