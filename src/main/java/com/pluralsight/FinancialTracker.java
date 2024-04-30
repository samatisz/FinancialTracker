package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {

        try {
            BufferedReader buff = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = buff.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String type = parts[2].trim();
                    String vendor = parts[3].trim();
                    double price = Double.parseDouble(parts[4]);
                    if (price >= 0) {
                        transactions.add(new Deposit(date, time, type, vendor, price));
                    } else {
                        transactions.add(new Payment(date, time, type, vendor, price));

                    }
                }
            }

            buff.close();
        } catch (Exception e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }

    }

    private static void addDeposit(Scanner myScanner) {
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
        System.out.println("Enter the date and time (yyyy-MM-dd HH:mm:ss) " );
        String dateAndTime = myScanner.nextLine();
        LocalDateTime dateTime = null; //nothing is here yet

        LocalDate date = null;
        LocalTime time = null;
        try {
            dateTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            date = dateTime.toLocalDate();
            time = dateTime.toLocalTime();

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date and time format. Please use (yyyy-MM-dd HH:mm:ss).");
            return;
        }
        System.out.println("Please enter the vendor name: ");
        String vendor = myScanner.nextLine();
        System.out.println("Enter your deposit amount: ");
        double amount = 0;

        try {
            amount = Double.parseDouble(myScanner.nextLine());
            if (amount <= 0) {
                System.out.println("Invalid! Please enter a positive number!");
                return;
            }

        } catch (Exception e) {
            System.out.println("Invalid format, please try again!");
            return;
        }

        Transaction deposit = new Deposit(date, time, "deposit", vendor, amount);
        transactions.add(deposit);


    }

    private static void addPayment(Scanner myScanner) {
        System.out.println("Please enter the date and time: ");
        String dateAndTime = myScanner.nextLine();
        System.out.println("Please enter the vendor: ");
        String vendor = myScanner.nextLine();
        System.out.println("Please enter the amount of payment:");
        double price = Double.parseDouble(myScanner.nextLine());
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            if(price <= 0){
                System.out.println("Invalid! Please enter a positive number!");
                return;
            }

            Transaction payment = new Payment(date, time, "payment", vendor, price);
            transactions.add(payment);

        } catch (Exception e) {
            System.out.println("Invalid format, please try again!");
        }

    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        for (Transaction transaction: transactions){
            //"transaction" is transaction.java while "transactions" is the array list (stored info in .csv)
            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" +  " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + transaction.getPrice() + "\n");
        }
        // The table should have columns for date, time, vendor, type, and amount.
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        for (Transaction transaction: transactions) {
            if (transaction instanceof Deposit) {
                //"transaction" is transaction.java while "transactions" is the array list (stored info in .csv)
                System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + transaction.getPrice() + "\n");
            }
        }
    }

    private static void displayPayments() {
        for (Transaction transaction: transactions) {
            if (transaction instanceof Payment) {
                //"transaction" is transaction.java while "transactions" is the array list (stored info in .csv)
                double makeItPositive = Math.abs(transaction.getPrice());
                System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + makeItPositive + "\n");
            }
        }
    }

    private static void reportsMenu(Scanner myScanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = myScanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("Here are all transactions from the current month.");

                    LocalDate currentDate = LocalDate.now();
                    Month currentMonth = currentDate.getMonth();

                    System.out.println("Current month is: " + currentMonth);

                    for(Transaction transaction: transactions){
                        Month workingDate = transaction.getDate().getMonth();
                        if(currentMonth == workingDate){
                            double makeItPositive = Math.abs(transaction.getPrice());
                            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + makeItPositive + "\n");
                        }
                    }

                    break;

                case "2":
                    System.out.println("Here are all transactions from the previous month.");

                    currentDate = LocalDate.now();
                    currentDate = currentDate.minusMonths(1);
                    currentMonth = currentDate.getMonth();

                    System.out.println("Previous month is: " + currentMonth);

                    for(Transaction transaction: transactions){
                        Month workingDate = transaction.getDate().getMonth();
                        if(currentMonth == workingDate){
                            double makeItPositive = Math.abs(transaction.getPrice());
                            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + makeItPositive + "\n");
                        }
                    }
                    break;

                case "3":
                    System.out.println("Here are all transactions from the current year.");

                    currentDate = LocalDate.now();
                    int currentYear = currentDate.getYear();

                    System.out.println("Current year is: " + currentYear);

                    for(Transaction transaction: transactions){
                        int workingDate = transaction.getDate().getYear();
                        if(currentYear == workingDate){
                            double makeItPositive = Math.abs(transaction.getPrice());
                            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + makeItPositive + "\n");
                        }
                    }
                    break;

                case "4":
                    System.out.println("Here are all transactions from the previous year.");

                    currentDate = LocalDate.now();
                    currentDate = currentDate.minusYears(1);
                    currentYear = currentDate.getYear();

                    System.out.println("The previous year is " + currentYear);

                    for(Transaction transaction: transactions){
                        int workingDate = transaction.getDate().getYear();
                        if(currentYear == workingDate){
                            double makeItPositive = Math.abs(transaction.getPrice());
                            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + makeItPositive + "\n");
                        }
                    }
                    break;

                case "5":
                    System.out.println("Please enter the name of the Vendor: ");
                    String vendorName = myScanner.nextLine();

                    for(Transaction transaction: transactions){
                        if(vendorName.equals(transaction.getVendor())){
                            double makeItPositive = Math.abs(transaction.getPrice());
                            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getType() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + makeItPositive + "\n");
                        }
                    }
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Please try again!");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
        boolean found = false;

    }


    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }


}