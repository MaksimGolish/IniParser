package controller;

import model.Bank;
import transaction.Operation;
import transaction.TransferRequest;

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

    public void cancelTransfer(TransferRequest transferRequest) {
        findBankByAccount(transferRequest.getSenderAccount())
                .cancelAsSender(transferRequest);
        findBankByAccount(transferRequest.getRecipient())
                .cancelAsReceiver(transferRequest);
    }

    public void cancelInnerOperation(Operation operation) {
        findBankByAccount(operation.getAccount())
                .cancelOperation(operation);
    }

    public Bank findBankByAccount(UUID id) {
        return banks.stream()
                .filter(bank -> bank.hasAccount(id))
                .findFirst()
                .orElse(null);
    }
}
