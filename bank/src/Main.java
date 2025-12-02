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

import java.util.Scanner;
import java.util.logging.Logger;


class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.println("+-------------------------+");
        System.out.println("| BANK ACCOUNT MANAGEMENT |");
        System.out.println("+-------------------------+");

        AccountManager accountManager = new AccountManager();
        TransactionManager transactionManager = new TransactionManager();
        CustomerManager customerManager = new CustomerManager();

        int choice = 0;
        try (Scanner scanner = new Scanner(System.in)) {

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
                System.out.println("7. Exit\n");

                choice = readInt(scanner, "Enter your choice: ", 1, 7);

                switch (choice) {
                    case 1:
                        createAccount(accountManager, customerManager, scanner);
                        break;
                    case 2:
                        viewAccounts(accountManager, scanner);
                        break;
                    case 3:
                        viewCustomers(customerManager, scanner);
                        break;
                    case 4:
                        processTransaction(accountManager, transactionManager, scanner);
                        break;
                    case 5:
                        viewTransactionHistory(transactionManager, scanner);
                        break;
                    case 6:
                        viewAllTransactionHistory(transactionManager, scanner);
                        break;
                    case 7:
                        break;
                    default:
                        logger.warning("Invalid Input. Try Again!");
                }

            } while (choice != 7);

        }

        logger.info("Thank you for using Bank Account Management System!");
        logger.info("Goodbye!");
    }

    public static void viewAllTransactionHistory(TransactionManager transactionManager, Scanner scanner) {
        transactionManager.viewAllTransactions(scanner);
    }

    public static void viewTransactionHistory(TransactionManager transactionManager, Scanner scanner) {
        System.out.println();
        System.out.println("+--------------------------+");
        System.out.println("| VIEW TRANSACTION HISTORY |");
        System.out.println("+--------------------------+");

        String accountNumber = readString(scanner, "\nEnter Account number: ");

        transactionManager.viewTransactionsByAccount(accountNumber, scanner);
    }


    public static void processTransaction(AccountManager accountManager, TransactionManager transactionManager, Scanner scanner) {
        System.out.println();
        System.out.println("+---------------------+");
        System.out.println("| PROCESS TRANSACTION |");
        System.out.println("+---------------------+");

        String accountNumber = readString(scanner, "\nEnter Account number: ");

        Account account;
        try {
            account = accountManager.findAccount(accountNumber);
        } catch (AccountNotFoundException e) {
            logger.warning(e.getMessage());
            return;
        }

        account.displayAccountDetails();

        System.out.println("\nSelect Transaction Type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");

        int transactionType = readInt(scanner, "Enter choice (1-2): ", 1, 2);

        double amount = readDouble(scanner, "Enter amount: ", 0);

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

        System.out.print("\nConfirm transaction? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.startsWith("y")) {
            logger.info("Transaction cancelled.");
            return;
        }

        try {
            if (transactionType == 1) {
                account.processTransaction(amount, "Deposit");
                logger.info("Deposit Successful! New Balance: $" + account.getBalance());
            } else if (transactionType == 2) {
                account.processTransaction(amount, "Withdrawal");
                logger.info("Withdrawal Successful! New Balance: $" + account.getBalance());
            }
            transactionManager.addTransaction(transaction);
        } catch (BankException e) {
            logger.severe("Transaction failed: " + e.getMessage());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();

    }


    public static void createAccount(AccountManager accountManager, CustomerManager customerManager, Scanner scanner) {
        System.out.println();
        System.out.println("+------------------+");
        System.out.println("| ACCOUNT CREATION |");
        System.out.println("+------------------+");

        Customer customer = createCustomer(scanner);
        customerManager.addCustomer(customer);
        Account account = createAccountForCustomer(scanner, customer);

        System.out.println();
        System.out.println("+--------------+");
        System.out.println("| Confirmation |");
        System.out.println("+--------------+");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Customer Type: " + (customer instanceof RegularCustomer ? "Regular" : "Premium"));
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Initial Deposit: $" + account.getBalance());

        System.out.print("\nConfirm account creation? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.startsWith("y")) {
            logger.info("Account creation cancelled.");
            return;
        }

        accountManager.addAccount(account);

        logger.info("Account Created Successfully!");

        account.displayAccountDetails();
        customer.displayCustomerDetails();

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();

    }

    private static Customer createCustomer(Scanner scanner) {
        String name = readString(scanner, "\nEnter customer name: ");
        int age = readInt(scanner, "Enter customer age: ", 0, 150);
        String contact = readString(scanner, "Enter customer contact: ");
        String address = readString(scanner, "Enter customer address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer (Standard banking services)");
        System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");

        int customerType = readInt(scanner, "\nSelect type (1-2): ", 1, 2);

        if (customerType == 1) {
            return new RegularCustomer(name, age, contact, address);
        } else {
            return new PremiumCustomer(name, age, contact, address);
        }
    }

    private static Account createAccountForCustomer(Scanner scanner, Customer customer) {
        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");

        int accountType = readInt(scanner, "\nSelect type (1-2): ", 1, 2);

        double minDeposit = (accountType == 1) ? 500.0 : 0.0;
        if (customer instanceof PremiumCustomer) {
            minDeposit = Math.max(minDeposit, 10000.0);
        }

        double initialDeposit = readDouble(scanner, "\nEnter initial deposit amount: ", minDeposit);

        if (accountType == 1) {
            return new SavingsAccount(customer, initialDeposit);
        } else {
            return new CheckingAccount(customer, initialDeposit);
        }
    }

    public static void viewAccounts(AccountManager accountManager, Scanner scanner) {
        accountManager.viewAllAccounts(scanner);
    }

    public static void viewCustomers(CustomerManager customerManager, Scanner scanner) {
        customerManager.viewAllCustomers(scanner);
    }

    // input validation functions
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    private static double readDouble(Scanner scanner, String prompt, double min) {
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= min) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number greater than or equal to " + min + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    private static String readString(Scanner scanner, String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                break;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return value;
    }
}