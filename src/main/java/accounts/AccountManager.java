package accounts;

import exceptions.AccountNotFoundException;
import utils.ConsoleTablePrinter;
import utils.TablePrinter;
import utils.InputReader;


public class AccountManager {

    private final Account[] accounts;
    private int accountCount;
    private final TablePrinter printer;

    private static final int MAX_ACCOUNTS = 50;

    public AccountManager() {
        this.accounts = new Account[MAX_ACCOUNTS];
        this.accountCount = 0;
        this.printer = new ConsoleTablePrinter();
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void addAccount(Account account) {
        if (accountCount < MAX_ACCOUNTS)
            this.accounts[this.accountCount++] = account;
        else
            System.out.println("Account limit reached.");
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (int i = 0; i < this.accountCount; i++) {
            if (this.accounts[i].getAccountNumber().equals(accountNumber)) {
                return this.accounts[i];
            }
        }
        throw new AccountNotFoundException("Account with number " + accountNumber + " not found.");
    }

    public void viewAllAccounts(InputReader inputReader) {
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

        String[][] data = buildTableData();

        printer.printTable(headers, data);

        System.out.println();
        System.out.println("Total Accounts: " + this.accountCount);
        System.out.println("Total Bank Balance: $" + getTotalBalance());

        inputReader.waitForEnter();
    }

    private String[][] buildTableData() {
        return java.util.stream.IntStream.range(0, this.accountCount)
                .mapToObj(i -> this.accounts[i])
                .map(acc -> new String[]{
                    acc.getAccountNumber(),
                    formatCustomerName(acc),
                    formatAccountType(acc),
                    formatAccountBalance(acc),
                    acc.getStatus()
                })
                .toArray(String[][]::new);
        }
        
        private String formatAccountBalance(Account account) {
        return "$" + String.format("%.2f", account.getBalance());
    }

    private String formatCustomerName(Account acc) {
        if (acc instanceof CheckingAccount checkingAccount) {
            return acc.getCustomer().getName() + " (Overdraft Limit: $" + checkingAccount.getOverdraftLimit() + ")";
        } else if (acc instanceof SavingsAccount savingsAccount) {
            return acc.getCustomer().getName() + " (Interest Rate: " + savingsAccount.getInterestRate() + "%)";
        }
        return acc.getCustomer().getName();
    }

    private String formatAccountType(Account acc) {
        if (acc instanceof CheckingAccount checkingAccount) {
            return acc.getAccountType() + " (Monthly Fee: $" + checkingAccount.getMonthlyFee() + ")";
        } else if (acc instanceof SavingsAccount savingsAccount) {
            return acc.getAccountType() + " (Min Balance: $" + savingsAccount.getMinimumBalance() + ")";
        }
        return acc.getAccountType();
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (int i = 0; i < this.accountCount; i++)
            totalBalance += this.accounts[i].getBalance();

        return totalBalance;
    }

}
