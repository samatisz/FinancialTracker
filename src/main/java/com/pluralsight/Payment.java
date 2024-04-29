package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Payment extends Transaction{
    // do not need the objects cause this is an extension of Transaction.java
    public Payment(LocalDate date, LocalTime time, String type, String vendor, double price) {
        super(date, time, type, vendor, price);
    }
}
