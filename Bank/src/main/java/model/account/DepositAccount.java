package model.account;

import exception.WithdrawalNotAvailableException;
import model.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public class DepositAccount extends Account {
    private final LocalDateTime expirationTime;
    private final double percent;

    public DepositAccount(int money, Client owner, LocalDateTime expirationTime, double percent) {
        super(money, owner);
        this.expirationTime = expirationTime;
        this.percent = percent;
    }

    @Override
    public void get(int amount) {
        if (!withdrawalAvailable())
            throw new WithdrawalNotAvailableException();
        money -= amount;
    }

    @Override
    public void add(int amount) {
        money += amount;
    }

    @Override
    public double getBalance() {
        return money;
    }

    @Override
    public boolean withdrawalAvailable() {
        return expirationTime.isAfter(LocalDateTime.now());
    }

    public void update() {
        money += money * percent / 365;
    }
}
