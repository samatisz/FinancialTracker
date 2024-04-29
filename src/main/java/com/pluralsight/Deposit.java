package com.pluralsight;

public class Deposit extends Transaction{
    private String date;
    private String time;
    private String type;
    private String vendor;
    private String price;

    public Deposit(String date, String time, String type, String vendor, double price) {
        super(date, time, type, vendor, price);
    }
}
