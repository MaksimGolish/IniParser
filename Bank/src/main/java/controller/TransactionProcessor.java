package controller;

import exception.AccountNotFoundException;
import model.Bank;
import model.TransferRequest;

import java.util.*;

public class TransactionProcessor {
    private static final TransactionProcessor instance = new TransactionProcessor();

    private final List<Bank> banks = new ArrayList<>();
    public static TransactionProcessor getInstance() {
        return instance;
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }

    public boolean proceedTransaction(TransferRequest transferRequest) {
        findBankByAccount(transferRequest.getRecipient())
                .accept(transferRequest);
        return true;
    }


    public void cancel(TransferRequest transferRequest) {
        findBankByAccount(transferRequest.getSender().getId())
                .cancelAsSender(transferRequest);
        findBankByAccount(transferRequest.getSender().getId())
                .cancelAsReceiver(transferRequest);
    }

    public Bank findBankByAccount(UUID id) {
        return banks.stream()
                .filter(bank -> bank.hasAccount(id))
                .findFirst()
                .orElse(null);
    }
}
