package model;

import checker.*;
import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import exception.TransactionNotFoundException;
import model.account.DebitAccount;
import service.BankService;
import lombok.*;
import model.account.Account;
import transaction.Operation;
import transaction.OperationType;
import transaction.Transaction;
import transaction.TransferRequest;

import java.util.*;

@Data
public class Bank {
    private final String name;
    private final UUID id = UUID.randomUUID();

    @Getter(value = AccessLevel.PRIVATE)
    private final BankService bankService;
    private final Map<UUID, Transaction> transactions;
    private double debitPercent;
    private RequestHandler requestHandler;

    public Bank(String name, double debitPercent) {
        this.name = name;
        bankService = new BankService();
        transactions = new HashMap<>();
        this.debitPercent = debitPercent;
        TransactionProcessor.getInstance().addBank(this);
        setupTransactions();
    }

    private void setupTransactions() {
        requestHandler = new ClientHandler();
        requestHandler
                .setNext(new ReceiverHandler())
                .setNext(new TransactionHandler())
                .setNext(new AccountBalanceHandler());
    }

    public void addClient(@NonNull Client client) {
        bankService.addClient(client);
    }

    public UUID createDebitAccount(UUID clientId) {
        Account account = DebitAccount.builder()
                .owner(bankService.getClient(clientId))
                .percent(debitPercent)
                .build();
        bankService.addAccount(account);
        return account.getId();
    }

    public void addMoney(UUID id, int amount) {
        bankService.getAccount(id).add(amount);
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

    public boolean hasAccount(UUID id) {
        return bankService.hasAccount(id);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions.values());
    }

    public double currentBalance(UUID account) {
        return bankService.getAccount(account).getMoney();
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
}
