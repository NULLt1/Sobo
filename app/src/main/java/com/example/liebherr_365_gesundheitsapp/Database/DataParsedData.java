package com.example.liebherr_365_gesundheitsapp.Database;

public class DataParsedData {
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
        return date;
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
