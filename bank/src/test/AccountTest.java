package test;

import accounts.CheckingAccount;
import accounts.SavingsAccount;
import customers.RegularCustomer;
import exceptions.BankException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void testSavingsAccountDeposit() throws BankException {
        RegularCustomer customer = new RegularCustomer("John Doe", 30, "1234567890", "123 Main St");
        SavingsAccount account = new SavingsAccount(customer, 1000.0);

        account.processTransaction(500.0, "Deposit");

        assertEquals(1500.0, account.getBalance(), "Balance should be 1500.0 after depositing 500.0");
    }

    @Test
    public void testSavingsAccountWithdrawalSuccess() throws BankException {
        RegularCustomer customer = new RegularCustomer("John Doe", 30, "1234567890", "123 Main St");
        SavingsAccount account = new SavingsAccount(customer, 1000.0);

        account.processTransaction(200.0, "Withdrawal");

        assertEquals(800.0, account.getBalance(), "Balance should be 800.0 after withdrawing 200.0");
    }

    @Test
    public void testSavingsAccountWithdrawalInsufficientFunds() {
        RegularCustomer customer = new RegularCustomer("John Doe", 30, "1234567890", "123 Main St");
        // Min balance is 500. Initial 1000. Can withdraw max 500.
        SavingsAccount account = new SavingsAccount(customer, 1000.0);

        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            account.processTransaction(600.0, "Withdrawal");
        });

        assertTrue(exception.getMessage().contains("Minimum balance requirement not met"), "Exception message should contain 'Minimum balance requirement not met'");
    }

    @Test
    public void testCheckingAccountOverdraftSuccess() throws BankException {
        RegularCustomer customer = new RegularCustomer("Jane Doe", 25, "0987654321", "456 Elm St");
        // Overdraft limit is 1000. Initial 100. Can withdraw up to 1100.
        CheckingAccount account = new CheckingAccount(customer, 100.0);

        account.processTransaction(500.0, "Withdrawal");

        assertEquals(-400.0, account.getBalance(), "Balance should be -400.0 (using overdraft)");
    }

    @Test
    public void testCheckingAccountOverdraftExceeded() {
        RegularCustomer customer = new RegularCustomer("Jane Doe", 25, "0987654321", "456 Elm St");
        // Overdraft limit is 1000. Initial 100. Max withdraw 1100.
        CheckingAccount account = new CheckingAccount(customer, 100.0);

        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            account.processTransaction(1200.0, "Withdrawal");
        });

        assertTrue(exception.getMessage().contains("Overdraft limit exceeded"), "Exception message should contain 'Overdraft limit exceeded'");
    }

    @Test
    public void testInvalidAmount() {
        RegularCustomer customer = new RegularCustomer("John Doe", 30, "1234567890", "123 Main St");
        SavingsAccount account = new SavingsAccount(customer, 1000.0);

        Exception exception = assertThrows(InvalidAmountException.class, () -> {
            account.processTransaction(-100.0, "Deposit");
        });

        assertEquals("Amount must be positive.", exception.getMessage(), "Exception message should be 'Amount must be positive.'");
    }
}
