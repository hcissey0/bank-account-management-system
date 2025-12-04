package accounts;

import customers.Customer;
import exceptions.BankException;
import exceptions.InvalidAmountException;
import transactions.Transactable;

/**
 * Represents an abstract bank account associated with a customer. This class serves as the base for
 * specific account types (e.g., Savings, Checking) and implements the {@link Transactable}
 * interface to handle financial operations.
 *
 * <p>It manages core account attributes such as:
 *
 * <ul>
 *   <li>Account Number (auto-generated)
 *   <li>Customer details
 *   <li>Account Status (defaults to "Active")
 *   <li>Current Balance
 * </ul>
 *
 * <p>The class provides concrete implementations for basic deposit and withdrawal logic, while
 * delegating specific validation rules and display logic to subclasses.
 *
 * @see Transactable
 * @see Customer
 * @see BankException
 */
public abstract class Account implements Transactable {
  private static final String DEFAULT_STATUS = "Active";
  private static int accountCounter = 0;
  private final String accountNumber;
  private final Customer customer;
  private final String status;
  private double balance;

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

  /**
   * Processes a financial transaction for the account based on the specified amount and transaction
   * type.
   *
   * <p>This method first validates the transaction details. If valid, it routes the request to
   * either the deposit or withdrawal logic based on the provided type string (case-insensitive).
   *
   * @param amount The monetary value of the transaction. Must be a positive value.
   * @param type The type of transaction to perform (e.g., "Deposit" or "Withdrawal").
   * @throws BankException If the amount is invalid, the transaction type is unrecognized, or if
   *     specific withdrawal conditions (like insufficient funds) are met.
   */
  @Override
  public void processTransaction(double amount, String type) throws BankException {
    validateAmount(amount, type);

    if (type.equalsIgnoreCase("Deposit")) this.deposit(amount);
    else if (type.equalsIgnoreCase("Withdrawal")) this.withdraw(amount);
    else throw new BankException("Invalid transaction type: " + type);
  }

  /**
   * Validates the transaction amount based on the transaction type (Deposit or Withdrawal).
   *
   * <p>This method delegates the validation logic to specific helper methods depending on whether
   * the operation is a "Deposit" or a "Withdrawal". The comparison for the transaction type is
   * case-insensitive.
   *
   * @param amount The monetary amount involved in the transaction.
   * @param type The type of transaction (e.g., "Deposit", "Withdrawal").
   * @throws BankException If the amount is invalid for the specified transaction type (e.g.,
   *     negative amount, insufficient funds).
   */
  @Override
  public void validateAmount(double amount, String type) throws BankException {
    if (type.equalsIgnoreCase("Deposit")) validateDeposit(amount);

    if (type.equalsIgnoreCase("Withdrawal")) validateWithdrawal(amount);
  }

  public void validateDeposit(double amount) throws InvalidAmountException {
    if (amount <= 0) throw new InvalidAmountException("Amount must be positive");
  }

  public abstract void displayAccountDetails();

  public abstract String getAccountType();

  protected abstract void validateWithdrawal(double amount) throws BankException;
}
