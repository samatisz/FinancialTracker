package com.pluralsight;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
            System.out.println("Welcome to MyTransactionApp!");
            System.out.println("============================");
            System.out.println("Choose an option:");
            System.out.println(" ");
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
                    String description = parts[2].trim();
                    String vendor = parts[3].trim();
                    double amount = Double.parseDouble(parts[4]);
                    if (amount >= 0) {
                        transactions.add(new Deposit(date, time, description, vendor, amount));
                    } else {
                        transactions.add(new Payment(date, time, description, vendor, amount));
                    }
                }
            }
            buff.close();
        } catch (Exception e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }

    }

    private static void addDeposit(Scanner myScanner) {

        System.out.println("Enter the date (yyyy-MM-dd): ");
        String dateString = myScanner.nextLine();
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);

        System.out.println("Please enter the time (HH:mm:ss): ");
        String timeString = myScanner.nextLine();
        LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);

        System.out.println("Please enter the description: ");
        String description = myScanner.nextLine();

        System.out.println("Please enter the vendor: ");
        String vendor = myScanner.nextLine();

        System.out.println("Please enter your deposit amount: ");
        double amount = Double.parseDouble(myScanner.nextLine());

        if (amount <= 0) {
            System.out.println("Invalid amount, Please try again");
            return;
        }
        Transaction deposit = new Deposit(date, time, description, vendor, amount);
        transactions.add(deposit);

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(date.format(DATE_FORMATTER) + "|" + time.format(TIME_FORMATTER) + "|" + description + "|" + vendor + "|" + amount);
            System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            writer.newLine();
            System.out.println("The deposit has been added successfully!");
            writer.close();
        } catch (Exception e) {
            System.out.println("Please enter again, invalid!");
        }
    }

    private static void addPayment(Scanner myScanner) {
        System.out.println("Please enter the date (yyyy-MM-dd-): ");
        String dateString = myScanner.nextLine();
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);

        System.out.println("Please enter the time (HH:mm:ss): ");
        String timeString = myScanner.nextLine();
        LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);

        System.out.println("Please enter description: ");
        String description = myScanner.nextLine();

        System.out.println("Please enter the vendor: ");
        String vendor = myScanner.nextLine();

        System.out.println("Please enter the payment amount: ");
        double amount = Double.parseDouble(myScanner.nextLine());
        if (amount <= 0) {
            System.out.println("Invalid amount! Please try again!");
            return;
        }
        amount *= -1;

        Transaction payment = (new Payment(date, time, description, vendor, amount));
        transactions.add(payment);


        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(date.format(DATE_FORMATTER)+ "|" + time.format(TIME_FORMATTER)+ "|" + description + "|" + vendor + "|" + amount);
            System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            writer.newLine();
            System.out.println("The payment has been added successfully!");
            writer.close();
        } catch (Exception e) {
            System.out.println("Invalid! Please try again!");
        }

    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("======");
            System.out.println("Choose an option:");
            System.out.println(" ");
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
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        for (Transaction transaction : transactions) {
            //"transaction" is transaction.java while "transactions" is the array list (stored info in .csv)
            System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getDescription() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + transaction.getAmount() + "\n");
            System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        }
    }

    private static void displayDeposits() {
        for (Transaction transaction : transactions) {
            if (transaction instanceof Deposit) {
                //"transaction" is transaction.java while "transactions" is the array list (stored info in .csv)
                System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Description: " + transaction.getDescription() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Amount: " + transaction.getAmount() + "\n");
                System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            }
        }
    }

    private static void displayPayments() {
        for (Transaction transaction : transactions) {
            if (transaction instanceof Payment) {
                //"transaction" is transaction.java while "transactions" is the array list (stored info in .csv)
                System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Description: " + transaction.getDescription() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Amount: " + transaction.getAmount() + "\n");
                System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            }
        }
    }

    private static void reportsMenu(Scanner myScanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("========");
            System.out.println("Choose an option:");
            System.out.println(" ");
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

                    LocalDate startOfMonth = currentDate.withDayOfMonth(1);
                    filterTransactionsByDate(startOfMonth, currentDate);

                    break;

                case "2":
                    System.out.println("Here are all transactions from the previous month.");

                   LocalDate previousDate = LocalDate.now().minusMonths(1);
                    currentMonth = previousDate.getMonth();

                    System.out.println("Previous month is: " + currentMonth);

                    startOfMonth = previousDate.withDayOfMonth(1);
                    LocalDate lastOfMonth = previousDate.with(TemporalAdjusters.lastDayOfMonth());
                    filterTransactionsByDate(startOfMonth, lastOfMonth);

                    break;

                case "3":
                    System.out.println("Here are all transactions from the current year.");

                    currentDate = LocalDate.now();
                    int currentYear = currentDate.getYear();

                    System.out.println("Current year is: " + currentYear);

                    LocalDate startOfYear = currentDate.withDayOfYear(1);
                    filterTransactionsByDate(startOfYear, currentDate);
                    break;

                case "4":
                    System.out.println("Here are all transactions from the previous year.");

                    LocalDate previousYear = LocalDate.now().minusYears(1);

                    System.out.println("The previous year is " + previousYear.getYear());

                    startOfYear = previousYear.withDayOfYear(1);
                    LocalDate lastOfYear = previousYear.with(TemporalAdjusters.lastDayOfYear());
                    filterTransactionsByDate(startOfYear, lastOfYear);
                    break;

                case "5":
                    System.out.println("Please enter the name of the Vendor: ");
                    String vendorName = myScanner.nextLine();
                    filterTransactionsByVendor(vendorName);

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
        boolean found = false;
        for (Transaction transaction : transactions) {

            if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getDescription() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + transaction.getAmount() + "\n");
                System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are no results found, please try again!");
        }
    }

    private static void filterTransactionsByVendor(String vendorName) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (vendorName.equalsIgnoreCase(transaction.getVendor())) {

                System.out.println(" Date: " + transaction.getDate() + "|" + " Time: " + transaction.getTime() + "|" + " Type: " + transaction.getDescription() + "|" + " Vendor: " + transaction.getVendor() + "|" + " Price: " + transaction.getAmount() + "\n");
                System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are no results found, please try again!");
        }
    }
}