package main;

import accounts.Account;
import accounts.AccountManager;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import customers.Customer;
import customers.CustomerManager;
import customers.PremiumCustomer;
import customers.RegularCustomer;
import exceptions.AccountNotFoundException;
import exceptions.BankException;
import transactions.Transaction;
import transactions.TransactionManager;
import utils.ConsoleInputReader;
import utils.CustomTestRunner;
import utils.InputReader;

class Main {

    public static void main(String[] args) {
        System.out.println("+-------------------------+");
        System.out.println("| BANK ACCOUNT MANAGEMENT |");
        System.out.println("+-------------------------+");

        AccountManager accountManager = new AccountManager();
        TransactionManager transactionManager = new TransactionManager();
        CustomerManager customerManager = new CustomerManager();

        int choice = 0;
        try (ConsoleInputReader inputReader = new ConsoleInputReader()) {

            do {
                System.out.println();
                System.out.println("+-----------+");
                System.out.println("| MAIN MENU |");
                System.out.println("+-----------+");
                System.out.println("1. Create Account");
                System.out.println("2. View Accounts");
                System.out.println("3. View Customers");
                System.out.println("4. Process Transaction");
                System.out.println("5. View Transaction History for an account");
                System.out.println("6. View all Transaction Histories");
                System.out.println("7. Generate Bank Statement");
                System.out.println("8. Run Tests");
                System.out.println("9. Exit\n");

                choice = inputReader.readInt("Enter your choice: ", 1, 9);

                switch (choice) {
                    case 1:
                        createAccount(accountManager, customerManager, inputReader);
                        break;
                    case 2:
                        viewAccounts(accountManager, inputReader);
                        break;
                    case 3:
                        viewCustomers(customerManager, inputReader);
                        break;
                    case 4:
                        processTransaction(accountManager, transactionManager, inputReader);
                        break;
                    case 5:
                        viewTransactionHistory(transactionManager, inputReader);
                        break;
                    case 6:
                        viewAllTransactionHistory(transactionManager, inputReader);
                        break;
                    case 7:
                        generateBankStatement(accountManager, transactionManager, inputReader);
                        break;
                    case 8:
                        runTests(inputReader);
                        break;
                    case 9:
                        break;
                    default:
                        System.out.println("Invalid Input. Try Again!");
                }

            } while (choice != 9);

        }

        System.out.println("Thank you for using Bank Account Management System!");
        System.out.println("Goodbye!");
    }

    public static void viewAllTransactionHistory(TransactionManager transactionManager, InputReader inputReader) {
        transactionManager.viewAllTransactions(inputReader);
    }

    public static void viewTransactionHistory(TransactionManager transactionManager, InputReader inputReader) {
        System.out.println();
        System.out.println("+--------------------------+");
        System.out.println("| VIEW TRANSACTION HISTORY |");
        System.out.println("+--------------------------+");

        String accountNumber = inputReader.readString("\nEnter Account number: ");

        transactionManager.viewTransactionsByAccount(accountNumber, inputReader);
    }

    public static void processTransaction(AccountManager accountManager, TransactionManager transactionManager,
            InputReader inputReader) {
        System.out.println();
        System.out.println("+---------------------+");
        System.out.println("| PROCESS TRANSACTION |");
        System.out.println("+---------------------+");

        String accountNumber = inputReader.readString("\nEnter Account number: ");

        Account account;
        try {
            account = accountManager.findAccount(accountNumber);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        account.displayAccountDetails();

        System.out.println("\nSelect Transaction Type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");

        int transactionType = inputReader.readInt("Enter choice (1-2): ", 1, 2);

        double amount = inputReader.readDouble("Enter amount: ", 0);

        String typeStr = (transactionType == 1) ? "Deposit" : "Withdrawal";

        double amountAfter = (transactionType == 1 ? account.getBalance() + amount : account.getBalance() - amount);
        Transaction transaction = new Transaction(
                account.getAccountNumber(),
                (transactionType == 1) ? "DEPOSIT" : "WITHDRAWAL",
                amount,
                amountAfter);

        System.out.println();
        System.out.println("+--------------------------+");
        System.out.println("| Transaction Confirmation |");
        System.out.println("+--------------------------+");
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Type: " + typeStr.toUpperCase());
        System.out.println("Amount: $" + amount);
        System.out.println("Previous Balance: $" + account.getBalance());
        System.out.println("New Balance: $" + amountAfter);
        System.out.println("Date/Time: " + transaction.getTimestamp());

        String confirm = inputReader.readString("\nConfirm transaction? (y/n): ").toLowerCase();

        if (!confirm.startsWith("y")) {
            System.out.println("Transaction cancelled.");
            return;
        }

        try {
            if (transactionType == 1) {
                account.processTransaction(amount, "Deposit");
                System.out.println("Deposit Successful! New Balance: $" + account.getBalance());
            } else if (transactionType == 2) {
                account.processTransaction(amount, "Withdrawal");
                System.out.println("Withdrawal Successful! New Balance: $" + account.getBalance());
            }
            transactionManager.addTransaction(transaction);
        } catch (BankException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }

        inputReader.waitForEnter();

    }

    public static void createAccount(AccountManager accountManager, CustomerManager customerManager,
            InputReader inputReader) {
        System.out.println();
        System.out.println("+------------------+");
        System.out.println("| ACCOUNT CREATION |");
        System.out.println("+------------------+");

        Customer customer = createCustomer(inputReader);
        customerManager.addCustomer(customer);
        Account account = createAccountForCustomer(inputReader, customer);

        System.out.println();
        System.out.println("+--------------+");
        System.out.println("| Confirmation |");
        System.out.println("+--------------+");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Customer Type: " + (customer instanceof RegularCustomer ? "Regular" : "Premium"));
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Initial Deposit: $" + account.getBalance());

        String confirm = inputReader.readString("\nConfirm account creation? (y/n): ").toLowerCase();

        if (!confirm.startsWith("y")) {
            System.out.println("Account creation cancelled.");
            return;
        }

        accountManager.addAccount(account);

        System.out.println("Account Created Successfully!");

        account.displayAccountDetails();
        customer.displayCustomerDetails();

        inputReader.waitForEnter();

    }

    private static Customer createCustomer(InputReader inputReader) {
        String name = inputReader.readString("\nEnter customer name: ");
        int age = inputReader.readInt("Enter customer age: ", 0, 150);
        String contact = inputReader.readString("Enter customer contact: ");
        String address = inputReader.readString("Enter customer address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer (Standard banking services)");
        System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");

        int customerType = inputReader.readInt("\nSelect type (1-2): ", 1, 2);

        if (customerType == 1) {
            return new RegularCustomer(name, age, contact, address);
        } else {
            return new PremiumCustomer(name, age, contact, address);
        }
    }

    private static Account createAccountForCustomer(InputReader inputReader, Customer customer) {
        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");

        int accountType = inputReader.readInt("\nSelect type (1-2): ", 1, 2);

        double minDeposit = (accountType == 1) ? 500.0 : 0.0;
        if (customer instanceof PremiumCustomer) {
            minDeposit = Math.max(minDeposit, 10000.0);
        }

        double initialDeposit = inputReader.readDouble("\nEnter initial deposit amount: ", minDeposit);

        if (accountType == 1) {
            return new SavingsAccount(customer, initialDeposit);
        } else {
            return new CheckingAccount(customer, initialDeposit);
        }
    }

    public static void viewAccounts(AccountManager accountManager, InputReader inputReader) {
        accountManager.viewAllAccounts(inputReader);
    }

    public static void viewCustomers(CustomerManager customerManager, InputReader inputReader) {
        customerManager.viewAllCustomers(inputReader);
    }

    public static void generateBankStatement(AccountManager accountManager, TransactionManager transactionManager, InputReader inputReader) {
        System.out.println();
        System.out.println("+----------------+");
        System.out.println("| BANK STATEMENT |");
        System.out.println("+----------------+");

        String accountNumber = inputReader.readString("\nEnter Account number: ");

        Account account;
        try {
            account = accountManager.findAccount(accountNumber);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            inputReader.waitForEnter();
            return;
        }

        // 1. Account Details
        account.displayAccountDetails();

        // 2. Transactions
        System.out.println("\n--- Transactions ---");
        Transaction[] transactions = transactionManager.getTransactionsForAccount(accountNumber);
        
        int count = 0;
        for(Transaction t : transactions) {
            if(t != null) count++;
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        } else {
            String[] headers = { "TRANSACTION ID", "TYPE", "AMOUNT", "DATE" };
            String[][] data = new String[count][4];
            int rowIndex = 0;
            for(Transaction t : transactions) {
                if(t != null) {
                    data[rowIndex][0] = t.getTransactionId();
                    data[rowIndex][1] = t.getType();
                    data[rowIndex][2] = "$" + t.getAmount();
                    data[rowIndex][3] = t.getTimestamp();
                    rowIndex++;
                }
            }
            
            new utils.ConsoleTablePrinter().printTable(headers, data);
        }

        // 3. Net Change
        double totalDeposits = transactionManager.getTotalDeposits(accountNumber);
        double totalWithdrawals = transactionManager.getTotalWithdrawals(accountNumber);
        double netChange = totalDeposits - totalWithdrawals;
        
        System.out.println("\n--- Summary ---");
        System.out.println("Total Deposits:    $" + String.format("%.2f", totalDeposits));
        System.out.println("Total Withdrawals: $" + String.format("%.2f", totalWithdrawals));
        System.out.println("Net Change:        $" + String.format("%.2f", netChange));
        System.out.println("Closing Balance:   $" + String.format("%.2f", account.getBalance()));
        
        inputReader.waitForEnter();
    }

    private static void runTests(InputReader inputReader) {
        System.out.println("Running tests...");
        try {
            CustomTestRunner runner = new CustomTestRunner();
            runner.runTests();
        } catch (Exception e) {
            System.out.println("Failed to run tests: " + e.getMessage());
            e.printStackTrace();
        }
        inputReader.waitForEnter();
    }
}
