package com.example.liebherr_365_gesundheitsapp.Database;

import android.util.Log;

import java.text.SimpleDateFormat;

public class DataParsedData {
    private static final String LOG_TAG = DataParsedData.class.getSimpleName();
    private String modul;
    private String date;
    private String teaser;
    private String text;
    private long id;
    private boolean flag;

    DataParsedData(Long id, String modul, String date, String teaser, String text, boolean flag) {
        this.id = id;
        this.modul = modul;
        this.date = date;
        this.teaser = teaser;
        this.text = text;
        this.flag = flag;
    }

    public long getId() {
        return id;
    }

    public String getModul() {
        return modul;
    }

    public String getTeaser() {
        return teaser;
    }

    public String getDate() {
        String formattedDate = date;
        //formats the Date to german date notation
        try {
            formattedDate = new SimpleDateFormat("dd.MM.yy").format(new SimpleDateFormat("yyyy.MM.dd").parse(date));
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
        }
        return formattedDate;
    }

    public String getText() {
        return text;
    }

    boolean getFlag() {
        return flag;
    }

    @Override
    public String toString() {

        return id + " " + modul + " " + date + " " + teaser + " " + text + " " + String.valueOf(flag);
    }
}
