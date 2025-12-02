package main.transactions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static int transactionCounter = 0;

    private final String transactionId;
    private final String accountNumber;
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final String timestamp;

    public Transaction(String accountNumber, String type, double amount, double balanceAfterTransaction) {
        this.transactionId = generateTransactionId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfterTransaction;
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.timestamp = formatter.format(time);
    }


    private static String generateTransactionId() { // Generates a transactionId
        return "TXN" + String.format("%03d", ++transactionCounter);
    }

    public static int getTransactionCounter() {
        return transactionCounter;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }
}
