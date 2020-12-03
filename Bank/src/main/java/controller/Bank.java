package controller;

import checker.RequestHandler;
import exception.AccountNotFoundException;
import exception.TransactionNotFoundException;
import lombok.Data;
import lombok.NonNull;
import model.Client;
import service.BankService;
import transaction.Operation;
import transaction.OperationType;
import transaction.Transaction;
import transaction.TransferRequest;

import java.util.*;

@Data
public abstract class Bank {
    private final String name;
    private final UUID id = UUID.randomUUID();

    protected final BankService bankService;
    protected final Map<UUID, Transaction> transactions;
    protected RequestHandler requestHandler;

    public Bank(String name) {
        this.name = name;
        bankService = new BankService();
        transactions = new HashMap<>();
        TransactionProcessor.getInstance().addBank(this);
        setupTransactions();
    }

    protected abstract void setupTransactions();

    public void addClient(@NonNull Client client) {
        bankService.addClient(client);
    }

    public boolean send(UUID accountId, int amount, UUID recipient) {
        TransferRequest request = bankService
                .getAccount(accountId)
                .createTransferRequest(amount, recipient);
        if (requestHandler.handle(request, bankService.getAccount(accountId))) {
            transactions.put(request.getId(), request);
            return true;
        }
        return false;
    }

    public void accept(TransferRequest transferRequest) {
        bankService
                .getAccount(transferRequest.getRecipient())
                .add(transferRequest.getAmount());
        transactions.put(transferRequest.getId(), transferRequest);
    }

    public void cancelAsSender(TransferRequest request) {
        if (!transactions.containsKey(request.getId()))
            throw new TransactionNotFoundException();
        if (!hasAccount(request.getSenderAccount()))
            throw new AccountNotFoundException(request.getSenderAccount());
        bankService
                .getAccount(request.getSenderAccount())
                .addForced(request.getAmount());
        transactions.remove(request.getId());
    }

    public void cancelAsReceiver(TransferRequest request) {
        if (!transactions.containsKey(request.getId()))
            throw new TransactionNotFoundException();
        if (!hasAccount(request.getRecipient()))
            throw new AccountNotFoundException(request.getRecipient());
        bankService
                .getAccount(request.getRecipient())
                .getForced(request.getAmount());
        transactions.remove(request.getId());
    }

    public void cancelOperation(Operation operation) {
        if (!transactions.containsKey(operation.getId()))
            throw new TransactionNotFoundException();
        if (!hasAccount(operation.getAccount()))
            throw new AccountNotFoundException(operation.getAccount());
        switch (operation.getType()) {
            case WITHDRAW:
                bankService
                        .getAccount(operation.getAccount())
                        .addForced(operation.getAmount());
                break;
            case CREDITING:
                bankService
                        .getAccount(operation.getAccount())
                        .getForced(operation.getAmount());
                break;
        }
        transactions.remove(operation.getId());
    }


    public double currentBalance(UUID account) {
        return bankService.getAccount(account).getMoney();
    }

    public boolean hasAccount(UUID id) {
        return bankService.hasAccount(id);
    }

    public void withdraw(UUID account, int amount) {
        bankService
                .getAccount(account)
                .get(amount);
        Operation operation = new Operation(
                account,
                OperationType.WITHDRAW,
                amount
        );
        transactions.put(operation.getId(), operation);
    }

    public void add(UUID account, int amount) {
        bankService
                .getAccount(account)
                .add(amount);
        Operation operation = new Operation(
                account,
                OperationType.CREDITING,
                amount
        );
        transactions.put(operation.getId(), operation);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions.values());
    }
}
