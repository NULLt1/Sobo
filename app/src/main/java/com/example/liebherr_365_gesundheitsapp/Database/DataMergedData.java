package com.example.liebherr_365_gesundheitsapp.Database;


public class DataMergedData {
    private String modul;
    private String date;
    private String header;
    private String text;
    private String price;

    public DataMergedData(String modul, String date, String header, String text, String price) {
        this.modul = modul;
        this.date = date;
        this.header = header;
        this.text = text;
        this.price = price;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String toString() {
        return "Modul: " + getModul() + " Date: " + getDate() + " Header: " + getHeader() + " Text: " + getText() + " Price: " + getPrice();
    }
}
