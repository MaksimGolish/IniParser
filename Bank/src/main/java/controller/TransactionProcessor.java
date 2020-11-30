package controller;

import exception.AccountNotFoundException;
import model.Bank;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BankService {
    private static final BankService instance = new BankService();

    private final List<Bank> banks = new ArrayList<>();

    public static BankService getInstance() {
        return instance;
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }

    public void proceedTransaction(Transaction transaction) {
    }
}
