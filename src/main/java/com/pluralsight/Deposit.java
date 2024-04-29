package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deposit extends Transaction{
// do not need the objects cause this is an extension of Transaction.java
    public Deposit(LocalDate date, LocalTime time, String type, String vendor, double price) {
        super(date, time, type, vendor, price);
    }
}
