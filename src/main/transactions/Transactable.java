package main.transactions;

import main.exceptions.BankException;

public interface Transactable {
    void processTransaction(double amount, String type) throws BankException;
    void validateAmount(double amount, String type) throws BankException;
}
