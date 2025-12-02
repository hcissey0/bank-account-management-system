package accounts;

import customers.Customer;
import exceptions.InsufficientFundsException;
import java.util.logging.Logger;

public class CheckingAccount extends Account {
    private static final Logger logger = Logger.getLogger(CheckingAccount.class.getName());
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
        logger.info("+-----------------+");
        logger.info("| Account Details |");
        logger.info("+-----------------+");
        logger.info("Account Number: " + this.getAccountNumber());
        logger.info("Customer: " + this.getCustomer().getName());
        logger.info("Account Type: " + this.getAccountType());
        logger.info("Current Balance: " + this.getBalance());
        logger.info("Overdraft: " + getOverdraftLimit());
        logger.info("Monthly fee: " + getMonthlyFee());
        logger.info("+--------------------------+");
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
