package accounts;

import customers.Customer;
import exceptions.InsufficientFundsException;

public class SavingsAccount extends Account {

    private final double interestRate;
    private final double minimumBalance;

    private static final double INTEREST_RATE = 3.5;
    private static final double MINIMUM_BALANCE = 500.0;
    private static final String ACCOUNT_TYPE = "Savings";

    public SavingsAccount(Customer customer, double initialDeposit) {
        super(customer);
        this.deposit(initialDeposit);
        this.interestRate = INTEREST_RATE;
        this.minimumBalance = MINIMUM_BALANCE;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double calculateInterest() {
        return this.getBalance() * this.interestRate;
    }

    @java.lang.Override
    public double withdraw(double amount) {

        if (this.getBalance() - amount >= this.minimumBalance) {
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
        System.out.println("Interest Rate: " + this.interestRate);
        System.out.println("Minimum Balance: " + this.minimumBalance);
        System.out.println("Interest: " + this.calculateInterest());
        System.out.println("+--------------------------+");

    }

    @java.lang.Override
    public String getAccountType() {
        return ACCOUNT_TYPE;
    }

    @Override
    protected void validateWithdrawal(double amount) throws InsufficientFundsException {
        if (this.getBalance() - amount < this.minimumBalance) {
            throw new InsufficientFundsException(
                "Transaction Failed: Insufficient funds to maintain minimum balance. Current Balance: "
                + this.getBalance() + ", Minimum Required Balance: " + this.minimumBalance
                );
        }
    }
}
