package accounts;

import customers.Customer;
import exceptions.BankException;
import exceptions.InvalidAmountException;
import transactions.Transactable;

public abstract class Account implements Transactable {
    private static int accountCounter = 0;
    private final String accountNumber;
    private final Customer customer;
    private double balance;
    private final String status;

    private static final String DEFAULT_STATUS = "Active";


    Account(Customer customer) {
        this.accountNumber = generateAccountNumber();
        this.balance = 0;
        this.customer = customer;
        this.status = DEFAULT_STATUS;
    }

    private static String generateAccountNumber() {
        return "ACC" + String.format("%03d", ++accountCounter);
    }

    // getters

    public static int getAccountCounter() {
        return accountCounter;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }

    // setters

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // methods

    public void deposit(double amount) {
        this.balance += amount;
    }

    public double withdraw(double amount) {
        this.balance -= amount;
        return this.balance;
    }

    @Override
    public void processTransaction(double amount, String type) throws BankException {
        validateAmount(amount, type);

        if (type.equalsIgnoreCase("Deposit"))
            this.deposit(amount);
        else if (type.equalsIgnoreCase("Withdrawal"))
            this.withdraw(amount);
        else
            throw new BankException("Invalid transaction type: " + type);
    }

    @Override
    public void validateAmount(double amount, String type) throws BankException {
        if (type.equalsIgnoreCase("Deposit"))
            validateDeposit(amount);

        if (type.equalsIgnoreCase("Withdrawal"))
            validateWithdrawal(amount);
    }

    public void validateDeposit(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive");
    }

    public abstract void displayAccountDetails();

    public abstract String getAccountType();

    protected abstract void validateWithdrawal(double amount) throws BankException;
}
