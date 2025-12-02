package main.accounts;

import main.customers.Customer;
import main.exceptions.InsufficientFundsException;

public class CheckingAccount extends Account {

    private final double overdraftLimit;
    private final double monthlyFee;

    public CheckingAccount(Customer customer, double initialDeposit) {
        super(customer);
        this.deposit(initialDeposit);
        this.overdraftLimit = 1000;
        this.monthlyFee = 10;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    /**
     * Deducts the monthly fee from the balance.
     */
    public void applyMonthlyFee() {
        if (getBalance() > this.monthlyFee) {
            this.setBalance(this.getBalance() - this.monthlyFee);
        }
    }

    @java.lang.Override
    public double withdraw(double amount) {
        if (amount - this.getBalance() < this.overdraftLimit) {
            return super.withdraw(amount);
        }
        return 0;
    }
    @java.lang.Override
    public void displayAccountDetails() {
        System.out.println("+-----------------+");
        System.out.println("| Account Details |");
        System.out.println("+-----------------+");
        System.out.println("Account Number: " + this.getAccountNumber());
        System.out.println("Customer: " + this.getCustomer().getName());
        System.out.println("Account Type: " + this.getAccountType());
        System.out.println("Current Balance: " + this.getBalance());
        System.out.println("Overdraft: " + getOverdraftLimit());
        System.out.println("Monthly fee: " + getMonthlyFee());
        System.out.println("+--------------------------+");
    }


    @java.lang.Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    protected void validateWithdrawal(double amount) throws InsufficientFundsException {
        if (amount - this.getBalance() > this.overdraftLimit) {
            throw new InsufficientFundsException("Withdrawal amount exceeds overdraft limit.");
        }
    }
}
