package controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import transaction.InnerOperation;
import transaction.Transaction;
import transaction.Transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class TransactionProcessor {
    @Getter
    private final static TransactionProcessor instance = new TransactionProcessor();
    private final List<Bank> banks = new ArrayList<>();

    public void addBank(Bank... banks) {
        this.banks.addAll(Arrays.asList(banks));
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
