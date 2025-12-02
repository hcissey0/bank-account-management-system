package transactions;

import utils.ConsoleTablePrinter;
import utils.TablePrinter;

import java.util.Scanner;
import java.util.logging.Logger;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
    private final Transaction[] transactions;
    private int transactionCount;
    private final TablePrinter printer;

    public TransactionManager() {
        this.transactions = new Transaction[200];
        this.transactionCount = 0;
        this.printer = new ConsoleTablePrinter();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions[this.transactionCount++] = transaction;
        logger.info("Transaction added: " + transaction.getTransactionId());
    }

    public double calculateTotalDeposits() {
        double total = 0;

        for (int i = 0; i < this.transactionCount; i++)
            if (this.transactions[i].getType().equals("Deposit"))
                total += this.transactions[i].getAmount();

        return total;
    }

    public double calculateTotalWithdrawals() {
        double total = 0;

        for (int i = 0; i < this.transactionCount; i++)
            if (this.transactions[i].getType().equals("Withdrawal"))
                total += this.transactions[i].getAmount();

        return total;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void viewAllTransactions(Scanner scanner) {
        String[] headers = {
                "TRANSACTION ID",
                "ACCOUNT NUMBER",
                "TYPE",
                "AMOUNT",
                "DATE"
        };

        if (this.transactionCount == 0) {
            logger.info("No transactions available.");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        String[][] data = new String[this.transactionCount][headers.length];
        int rowIndex = 0;
        for (int i = this.transactionCount - 1; i >= 0; i--) {
            Transaction tx = transactions[i];
            data[rowIndex][0] = tx.getTransactionId();
            data[rowIndex][1] = tx.getAccountNumber();
            data[rowIndex][2] = tx.getType();
            data[rowIndex][3] = (tx.getType().equalsIgnoreCase("DEPOSIT") ? "+$" : "-$") + tx.getAmount();
            data[rowIndex][4] = tx.getTimestamp();
            rowIndex++;
        }

        printer.printTable(headers, data);

        logger.info("Number of transactions: " + this.transactionCount);
        logger.info("Total Deposits: $" + this.calculateTotalDeposits());
        logger.info("Total Withdrawals: $" + this.calculateTotalWithdrawals());

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void viewTransactionsByAccount(String accountNumber, Scanner scanner) {
        String[] headers = {
                "TRANSACTION ID",
                "ACCOUNT NUMBER",
                "TYPE",
                "AMOUNT",
                "DATE"
        };

        int count = 0;
        for (int i = 0; i < this.transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                count++;
            }
        }

        if (count == 0) {
            logger.info("No transactions recorded for this account.");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        String[][] data = new String[count][headers.length];
        double totalDeposits = 0;
        double totalWithdrawals = 0;

        int rowIndex = 0;
        for (int i = this.transactionCount - 1; i >= 0; i--) {
            Transaction tx = transactions[i];
            if (tx.getAccountNumber().equals(accountNumber)) {
                data[rowIndex][0] = tx.getTransactionId();
                data[rowIndex][1] = tx.getAccountNumber();
                data[rowIndex][2] = tx.getType();
                data[rowIndex][3] = (tx.getType().equalsIgnoreCase("DEPOSIT") ? "+$" : "-$") + tx.getAmount();
                data[rowIndex][4] = tx.getTimestamp();

                if (tx.getType().equals("DEPOSIT"))
                    totalDeposits += tx.getAmount();
                else
                    totalWithdrawals += tx.getAmount();
                rowIndex++;
            }
        }

        printer.printTable(headers, data);

        logger.info("Number of transactions: " + count);
        logger.info("Total Deposits: $" + totalDeposits);
        logger.info("Total Withdrawals: $" + totalWithdrawals);

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }


}
