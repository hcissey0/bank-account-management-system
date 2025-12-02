package accounts;

import customers.Customer;

import java.util.logging.Logger;

public class SavingsAccount extends Account {

    private static final Logger logger = Logger.getLogger(SavingsAccount.class.getName());
    private final double interestRate;
    private final double minimumBalance;

    public SavingsAccount(Customer customer, double initialDeposit) {
        super(customer);
        this.deposit(initialDeposit);
        this.interestRate = 3.5;
        this.minimumBalance = 500.0;
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
        logger.info("+-----------------+");
        logger.info("| Account Details |");
        logger.info("+-----------------+");
        logger.info("Account Number: " + this.getAccountNumber());
        logger.info("Customer: " + this.getCustomer().getName());
        logger.info("Account Type: " + this.getAccountType());
        logger.info("Current Balance: " + this.getBalance());
        logger.info("Interest Rate: " + this.interestRate);
        logger.info("Minimum Balance: " + this.minimumBalance);
        logger.info("Interest: " + this.calculateInterest());
        logger.info("+--------------------------+");

    }

    @java.lang.Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    protected void validateWithdrawal(double amount) throws exceptions.InsufficientFundsException {
        if (this.getBalance() - amount < this.minimumBalance) {
            throw new exceptions.InsufficientFundsException("Withdrawal would breach minimum balance requirement.");
        }
    }
}
