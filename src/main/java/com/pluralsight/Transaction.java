package com.pluralsight;

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


    public String getVendor() {
        return vendor;
    }


    public String getType() {
        return type;
    }


    public String getTime() {
        return time;
    }


    public String getDate() {
        return date;
    }

}