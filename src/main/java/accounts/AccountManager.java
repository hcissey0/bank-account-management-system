package accounts;

import exceptions.AccountNotFoundException;
import utils.ConsoleTablePrinter;
import utils.TablePrinter;


public class AccountManager {

    private final Account[] accounts;
    private int accountCount;
    private final TablePrinter printer;

    public AccountManager() {
        this.accounts = new Account[50];
        this.accountCount = 0;
        this.printer = new ConsoleTablePrinter();
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void addAccount(Account account) {
        this.accounts[this.accountCount++] = account;
        System.out.println("Account added: " + account.getAccountNumber());
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (int i = 0; i < this.accountCount; i++) {
            if (this.accounts[i].getAccountNumber().equals(accountNumber)) {
                return this.accounts[i];
            }
        }
        throw new AccountNotFoundException("Account with number " + accountNumber + " not found.");
    }

    public void viewAllAccounts(utils.InputReader inputReader) {
        String[] headers = {
                "ACCOUNT NUMBER",
                "CUSTOMER NAME",
                "TYPE",
                "BALANCE",
                "STATUS"
        };

        if (accountCount == 0) {
            System.out.println("No accounts available.");
            inputReader.waitForEnter();
            return;
        }

        String[][] data = new String[this.accountCount][headers.length];
        for (int i = 0; i < this.accountCount; i++) {
            Account acc = this.accounts[i];
            data[i][0] = acc.getAccountNumber();
            data[i][1] = (acc instanceof CheckingAccount checkingAccount
                    ? acc.getCustomer().getName()
                            .concat(" (Overdraft Limit: $" + checkingAccount.getOverdraftLimit() + ")")
                    : acc.getCustomer().getName()
                            .concat(" (Interest Rate: " + ((SavingsAccount) acc).getInterestRate() + "%)"));
            data[i][2] = (acc instanceof CheckingAccount checkingAccount
                    ? acc.getAccountType().concat(" (Monthly Fee: $" + checkingAccount.getMonthlyFee() + ")")
                    : acc.getAccountType()
                            .concat(" (Min Balance: $" + ((SavingsAccount) acc).getMinimumBalance() + ")"));
            data[i][3] = "$" + acc.getBalance();
            data[i][4] = acc.getStatus();
        }

        printer.printTable(headers, data);

        System.out.println();
        System.out.println("Total Accounts: " + this.accountCount);
        System.out.println("Total Bank Balance: $" + getTotalBalance());

        inputReader.waitForEnter();

    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (int i = 0; i < this.accountCount; i++)
            totalBalance += this.accounts[i].getBalance();

        return totalBalance;
    }

}
