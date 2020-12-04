package controller;

import transaction.InnerOperation;
import transaction.Transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionProcessor {
    private static final TransactionProcessor instance = new TransactionProcessor();

    private final List<Bank> banks = new ArrayList<>();
    public static TransactionProcessor getInstance() {
        return instance;
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }

    public boolean proceedTransaction(Transfer transfer) {
        findBankByAccount(transfer.getRecipient())
                .accept(transfer);
        return true;
    }

    public void cancelTransfer(Transfer transfer) {
        findBankByAccount(transfer.getSenderAccount())
                .cancelAsSender(transfer);
        findBankByAccount(transfer.getRecipient())
                .cancelAsReceiver(transfer);
    }

    public void cancelInnerOperation(InnerOperation operation) {
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
