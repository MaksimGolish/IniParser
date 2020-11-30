package model;

import checker.*;
import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import model.account.DebitAccount;
import service.BankService;
import lombok.*;
import model.account.Account;

import java.util.*;

@Data
public class Bank {
    private final String name;
    private final UUID id = UUID.randomUUID();

    @Getter(value = AccessLevel.PRIVATE)
    private final BankService bankService;
    private final Map<UUID, TransferRequest> transactions;
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
        requestHandler = new ClientHandler()
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

    public boolean send(UUID accountId, int amount, UUID recipient) {
        return requestHandler.handle(
                bankService
                        .getAccount(accountId)
                        .createTransferRequest(amount, recipient),
                bankService
                        .getAccount(accountId));
    }

    public void addMoney(UUID id, int amount) {
        bankService.getAccount(id).add(amount);
    }

    public void accept(TransferRequest transferRequest) {
        bankService
                .getAccount(transferRequest.getRecipient())
                .add(transferRequest.getAmount());
    }

    public void cancelAsSender(TransferRequest request) {
        if (!hasAccount(request.getSender().getId()))
            throw new AccountNotFoundException(request.getSender().getId());
        bankService
                .getAccount(request.getSender().getId())
                .addForced(request.getAmount());
    }

    public boolean hasAccount(UUID id) {
        return bankService.hasAccount(id);
    }

    public List<TransferRequest> getTransactions() {
        return new ArrayList<>(transactions.values());
    }

    public double currentMoneyAmount(UUID account) {
        return bankService.getAccount(account).getMoney();
    }

    public Account getAccount(UUID id) {
        return bankService.getAccount(id);
    }

    public void cancelAsReceiver(TransferRequest request) {
        if (!hasAccount(request.getRecipient()))
            throw new AccountNotFoundException(request.getRecipient());
        bankService
                .getAccount(request.getRecipient())
                .getForced(request.getAmount());
    }
}
