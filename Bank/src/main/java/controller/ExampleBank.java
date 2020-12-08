package controller;

import checker.AccountBalanceHandler;
import checker.ClientHandler;
import checker.ReceiverHandler;
import checker.TransactionHandler;
import exception.ClientNotFoundException;
import exception.IllegalPercentException;
import lombok.Builder;
import lombok.NonNull;
import model.Client;
import model.account.Account;
import model.account.CreditAccount;
import model.account.DebitAccount;
import model.account.DepositAccount;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExampleBank extends Bank {
    private final double debitPercent;
    private final double creditPercent;

    @Builder
    public ExampleBank(@NonNull String name,
                       @NonNull Double debitPercent,
                       @NonNull Double creditPercent) {
        super(name);
        if (debitPercent < 0 || creditPercent < 0)
            throw new IllegalPercentException();
        this.debitPercent = debitPercent;
        this.creditPercent = creditPercent;
    }

    @Override
    protected void setupTransactions() {
        requestHandler = new ClientHandler(10000);
        requestHandler
                .setNext(new ReceiverHandler())
                .setNext(new TransactionHandler())
                .setNext(new AccountBalanceHandler());
    }

    public UUID createDebitAccount(UUID clientId) {
        Client client = bankService.getClient(clientId);
        if (client == null)
            throw new ClientNotFoundException(clientId);
        Account account = DebitAccount
                .builder()
                .owner(client)
                .percent(debitPercent)
                .build();
        bankService.addAccount(account);
        return account.getId();
    }

    public UUID createCreditAccount(UUID clientId, double limit) {
        Client client = bankService.getClient(clientId);
        if (client == null)
            throw new ClientNotFoundException(clientId);
        Account account = CreditAccount
                .builder()
                .owner(bankService.getClient(clientId))
                .percent(creditPercent)
                .money(limit)
                .build();
        bankService.addAccount(account);
        return account.getId();
    }

    public UUID createDepositAccount(UUID clientId, double money, LocalDateTime expirationTime) {
        Client client = bankService.getClient(clientId);
        if (client == null)
            throw new ClientNotFoundException(clientId);
        Account account = DepositAccount
                .builder()
                .owner(bankService.getClient(clientId))
                .expirationTime(expirationTime)
                .money(money)
                .build();
        bankService.addAccount(account);
        return account.getId();
    }
}
