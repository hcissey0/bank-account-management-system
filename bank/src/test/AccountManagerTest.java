package test;

import accounts.Account;
import accounts.AccountManager;
import accounts.SavingsAccount;
import customers.RegularCustomer;
import exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {

    @Test
    public void testAddAndFindAccount() throws AccountNotFoundException {
        AccountManager manager = new AccountManager();
        RegularCustomer customer = new RegularCustomer("Alice", 28, "1112223333", "789 Oak St");
        SavingsAccount account = new SavingsAccount(customer, 1000.0);

        manager.addAccount(account);

        Account foundAccount = manager.findAccount(account.getAccountNumber());
        assertNotNull(foundAccount, "Found account should not be null");
        assertEquals(account.getAccountNumber(), foundAccount.getAccountNumber(), "Account numbers should match");
    }

    @Test
    public void testFindAccountNotFound() {
        AccountManager manager = new AccountManager();

        Exception exception = assertThrows(AccountNotFoundException.class, () -> {
            manager.findAccount("NON_EXISTENT_ACC");
        });

        assertTrue(exception.getMessage().contains("not found"), "Exception message should contain 'not found'");
    }
}
