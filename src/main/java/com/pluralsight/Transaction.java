package com.pluralsight;

import java.sql.Time;
import java.util.Date;

public class Transaction {
    private String date;
    private String time;
    private String type;
    private String vendor;
    private double price;

    public Transaction(String date, String time, String type, String vendor, double price) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.vendor = vendor;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}