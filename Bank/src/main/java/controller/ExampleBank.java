package controller;

import checker.*;
import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import exception.IllegalPercentException;
import exception.TransactionNotFoundException;
import model.Client;
import model.account.CreditAccount;
import model.account.DebitAccount;
import model.account.DepositAccount;
import service.BankService;
import lombok.*;
import model.account.Account;
import transaction.Operation;
import transaction.OperationType;
import transaction.Transaction;
import transaction.TransferRequest;

import java.time.LocalDateTime;
import java.util.*;

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
        Account account = DebitAccount
                .builder()
                .owner(bankService.getClient(clientId))
                .percent(debitPercent)
                .build();
        bankService.addAccount(account);
        return account.getId();
    }

    public UUID createCreditAccount(UUID clientId, double limit) {
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
