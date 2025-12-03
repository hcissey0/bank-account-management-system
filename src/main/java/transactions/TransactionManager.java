package transactions;

import utils.ConsoleTablePrinter;
import utils.TablePrinter;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TransactionManager {

    private static final int MAX_TRANSACTIONS = 200;
    private static final String DEPOSIT_TYPE = "DEPOSIT";
    private static final String WITHDRAWAL_TYPE = "WITHDRAWAL";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final Transaction[] transactions;
    private int transactionCount;
    private final TablePrinter printer;

    public TransactionManager() {
        this.transactions = new Transaction[MAX_TRANSACTIONS];
        this.transactionCount = 0;
        this.printer = new ConsoleTablePrinter();
    }

    /**
     * Adds a transaction to the transaction history
     * 
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            System.out.println("Attempted to add null transaction");
            return;
        }

        if (transactionCount >= MAX_TRANSACTIONS) {
            System.out.println("Transaction limit reached. Cannot add more transactions.");
            return;
        }

        this.transactions[this.transactionCount++] = transaction;
        System.out.println("Transaction added: " + transaction.getTransactionId());
    }

    /**
     * Calculates the total amount of all deposits
     * 
     * @return Total deposit amount
     */
    public double calculateTotalDeposits() {
        return calculateTotalByType(DEPOSIT_TYPE);
    }

    public double calculateTotalWithdrawals() {
        return calculateTotalByType(WITHDRAWAL_TYPE);
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void viewAllTransactions(utils.InputReader inputReader) {
        if (isTransactionListEmpty(inputReader)) {
            return;
        }

        String[] headers = createTransactionHeaders();
        String[][] data = buildTransactionData(transactions, transactionCount);

        printer.printTable(headers, data);
        displayTransactionSummary(transactionCount, calculateTotalDeposits(), calculateTotalWithdrawals());

        waitForUserInput(inputReader);
    }

    public void viewTransactionsByAccount(String accountNumber, utils.InputReader inputReader) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            System.out.println("Invalid account number provided");
            inputReader.waitForEnter();
            return;
        }

        Transaction[] accountTransactions = filterTransactionsByAccount(accountNumber);
        int count = countNonNullTransactions(accountTransactions);

        if (count == 0) {
            System.out.println("No transactions recorded for account: " + accountNumber);
            inputReader.waitForEnter();
            return;
        }

        String[] headers = createTransactionHeaders();
        String[][] data = buildTransactionData(accountTransactions, count);

        printer.printTable(headers, data);

        double totalDeposits = calculateTotalByTypeForAccount(accountNumber, DEPOSIT_TYPE);
        double totalWithdrawals = calculateTotalByTypeForAccount(accountNumber, WITHDRAWAL_TYPE);
        displayTransactionSummary(count, totalDeposits, totalWithdrawals);

        waitForUserInput(inputReader);
    }

    // ==================== HELPER METHODS ====================

    /**
     * Helper method to calculate total amount by transaction type
     * 
     * @param type The transaction type (DEPOSIT or WITHDRAWAL)
     * @return Total amount for the specified type
     */
    private double calculateTotalByType(String type) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i] != null && transactions[i].getType().equalsIgnoreCase(type)) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    /**
     * Helper method to filter transactions by account number
     * 
     * @param accountNumber The account number to filter by
     * @return Array of transactions for the specified account
     */
    private Transaction[] filterTransactionsByAccount(String accountNumber) {
        Transaction[] filtered = new Transaction[transactionCount];
        int index = 0;

        for (int i = transactionCount - 1; i >= 0; i--) {
            if (transactions[i] != null && transactions[i].getAccountNumber().equals(accountNumber)) {
                filtered[index++] = transactions[i];
            }
        }

        return filtered;
    }

    /**
     * Helper method to count non-null transactions in an array
     * 
     * @param transactionArray Array of transactions
     * @return Count of non-null transactions
     */
    private int countNonNullTransactions(Transaction[] transactionArray) {
        int count = 0;
        for (Transaction tx : transactionArray) {
            if (tx != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Helper method to create standard transaction table headers
     * 
     * @return Array of header strings
     */
    private String[] createTransactionHeaders() {
        return new String[] {
                "TRANSACTION ID",
                "ACCOUNT NUMBER",
                "TYPE",
                "AMOUNT",
                "DATE"
        };
    }

    /**
     * Helper method to build transaction data for table display
     * 
     * @param transactionArray Array of transactions to display
     * @param count            Number of transactions to process
     * @return 2D array of transaction data
     */
    private String[][] buildTransactionData(Transaction[] transactionArray, int count) {
        String[][] data = new String[count][5];
        int rowIndex = 0;

        for (int i = 0; i < transactionArray.length && rowIndex < count; i++) {
            Transaction tx = transactionArray[i];
            if (tx != null) {
                data[rowIndex][0] = tx.getTransactionId();
                data[rowIndex][1] = tx.getAccountNumber();
                data[rowIndex][2] = tx.getType().toUpperCase();
                data[rowIndex][3] = formatAmount(tx.getType(), tx.getAmount());
                data[rowIndex][4] = tx.getTimestamp();
                rowIndex++;
            }
        }

        return data;
    }

    /**
     * Helper method to format transaction amount with sign
     * 
     * @param type   Transaction type
     * @param amount Transaction amount
     * @return Formatted amount string
     */
    private String formatAmount(String type, double amount) {
        String prefix = type.equalsIgnoreCase(DEPOSIT_TYPE) ? "+$" : "-$";
        return String.format("%s%.2f", prefix, amount);
    }

    /**
     * Helper method to display transaction summary statistics
     * 
     * @param count            Number of transactions
     * @param totalDeposits    Total deposit amount
     * @param totalWithdrawals Total withdrawal amount
     */
    private void displayTransactionSummary(int count, double totalDeposits, double totalWithdrawals) {
        System.out.println("Number of transactions: " + count);
        System.out.println(String.format("Total Deposits: $%.2f", totalDeposits));
        System.out.println(String.format("Total Withdrawals: $%.2f", totalWithdrawals));
    }

    /**
     * Helper method to calculate total by type for a specific account
     * 
     * @param accountNumber The account number
     * @param type          The transaction type
     * @return Total amount
     */
    private double calculateTotalByTypeForAccount(String accountNumber, String type) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i] != null
                    && transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase(type)) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    /**
     * Helper method to check if transaction list is empty
     * 
     * @param inputReader InputReader for user input
     * @return true if empty, false otherwise
     */
    private boolean isTransactionListEmpty(utils.InputReader inputReader) {
        if (transactionCount == 0) {
            System.out.println("No transactions available.");
            inputReader.waitForEnter();
            return true;
        }
        return false;
    }

    /**
     * Helper method to wait for user input before continuing
     * 
     * @param inputReader InputReader for user input
     */
    private void waitForUserInput(utils.InputReader inputReader) {
        inputReader.waitForEnter();
    }
}
