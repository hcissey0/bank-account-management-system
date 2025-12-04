package accounts;

import exceptions.AccountNotFoundException;
import utils.ConsoleTablePrinter;
import utils.InputReader;
import utils.TablePrinter;

/**
 * Manages a collection of bank accounts, providing functionality to add, retrieve, and view account
 * details.
 *
 * <p>This class handles the storage of {@link Account} objects up to a fixed maximum limit. It
 * facilitates operations such as calculating the total balance across all accounts, searching for
 * specific accounts by their unique number, and displaying formatted tabular data of all current
 * accounts via a {@link TablePrinter}.
 *
 * <p>Key features include:
 *
 * <ul>
 *   <li>Fixed capacity storage (defined by {@code MAX_ACCOUNTS}).
 *   <li>Account lookup with exception handling for non-existent accounts.
 *   <li>Formatted console output for account summaries, including specific details based on account
 *       type (e.g., Checking vs. Savings).
 * </ul>
 */
public class AccountManager {
  private static final int MAX_ACCOUNTS = 50;

  private final Account[] accounts;
  private int accountCount;
  private final TablePrinter printer;

  public AccountManager() {
    this.accounts = new Account[MAX_ACCOUNTS];
    this.accountCount = 0;
    this.printer = new ConsoleTablePrinter();
  }

  public int getAccountCount() {
    return accountCount;
  }

  public void addAccount(Account account) {
    if (accountCount < MAX_ACCOUNTS) this.accounts[this.accountCount++] = account;
    else System.out.println("Account limit reached.");
  }

  public Account findAccount(String accountNumber) throws AccountNotFoundException {
    for (int i = 0; i < this.accountCount; i++) {
      if (this.accounts[i].getAccountNumber().equals(accountNumber)) {
        return this.accounts[i];
      }
    }
    throw new AccountNotFoundException("Account with number " + accountNumber + " not found.");
  }

  /**
   * Displays a tabular view of all registered bank accounts to the console.
   *
   * <p>This method performs the following operations:
   *
   * <ul>
   *   <li>Checks if there are any accounts; if not, displays a message and returns.
   *   <li>Constructs a data table containing account details (Number, Name, Type, Balance, Status).
   *   <li>Uses the configured printer to render the table with headers.
   *   <li>Prints summary statistics including the total count of accounts and the total bank
   *       balance.
   *   <li>Pauses execution until the user presses Enter.
   * </ul>
   *
   * @param inputReader The utility object used to handle user input, specifically for pausing
   *     execution.
   */
  public void viewAllAccounts(InputReader inputReader) {
    String[] headers = {"ACCOUNT NUMBER", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS"};

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

  /**
   * Constructs a two-dimensional array of String data representing the current list of accounts.
   *
   * <p>This method iterates through the active accounts managed by this instance and transforms
   * each account object into a row of formatted string values suitable for display in a tabular
   * format (e.g., console output). The resulting columns for each row are:
   *
   * <ol>
   *   <li>Account Number
   *   <li>Customer Name (formatted)
   *   <li>Account Type (formatted)
   *   <li>Account Balance (formatted)
   *   <li>Account Status
   * </ol>
   *
   * @return A {@code String[][]} where each row corresponds to an account and each column contains
   *     specific account details. Returns an empty array if no accounts exist.
   */
  private String[][] buildTableData() {
    return java.util.stream.IntStream.range(0, this.accountCount)
        .mapToObj(i -> this.accounts[i])
        .map(
            acc ->
                new String[] {
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

  /**
   * Formats the customer name associated with an account, appending specific account details based
   * on the account type.
   *
   * <p>If the account is a {@code CheckingAccount}, the overdraft limit is appended. If the account
   * is a {@code SavingsAccount}, the interest rate is appended. For other account types, only the
   * customer name is returned.
   *
   * @param acc the account for which to format the customer name
   * @return a string containing the customer's name, optionally followed by account-specific
   *     details
   */
  private String formatCustomerName(Account acc) {
    if (acc instanceof CheckingAccount checkingAccount) {
      return acc.getCustomer().getName()
          + " (Overdraft Limit: $"
          + checkingAccount.getOverdraftLimit()
          + ")";
    } else if (acc instanceof SavingsAccount savingsAccount) {
      return acc.getCustomer().getName()
          + " (Interest Rate: "
          + savingsAccount.getInterestRate()
          + "%)";
    }
    return acc.getCustomer().getName();
  }

  /**
   * Formats the account type string based on the specific subclass of the account.
   *
   * <p>This method inspects the runtime type of the provided {@code Account} object. If the account
   * is a {@code CheckingAccount}, it appends the monthly fee to the account type. If the account is
   * a {@code SavingsAccount}, it appends the minimum balance requirement. For other account types,
   * it returns the standard account type string.
   *
   * @param acc the account object to format
   * @return a formatted string representing the account type and specific details (e.g., fee or
   *     minimum balance)
   */
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
    for (int i = 0; i < this.accountCount; i++) totalBalance += this.accounts[i].getBalance();

    return totalBalance;
  }
}
