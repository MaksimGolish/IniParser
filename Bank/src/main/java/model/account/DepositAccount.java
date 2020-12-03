package model.account;

import exception.WithdrawalNotAvailableException;
import model.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public class DepositAccount extends Account {
    private final LocalDateTime expirationTime;
    private final double percent;
    private boolean isActual = false;

    public DepositAccount(int money, Client owner, LocalDateTime expirationTime, double percent) {
        super(money, owner);
        this.expirationTime = expirationTime;
        this.percent = percent;
    }

    @Override
    public void get(int amount) {
        if (!withdrawalAvailable())
            throw new WithdrawalNotAvailableException();
        recalculate();
        money -= amount;
    }

    @Override
    public void add(int amount) {
        money += amount;
    }

    @Override
    public double getBalance() {
        if (withdrawalAvailable())
            recalculate();
        return money;
    }

    @Override
    public boolean withdrawalAvailable() {
        return expirationTime.isBefore(LocalDateTime.now());
    }

    public void recalculate() {
        if (!isActual)
            money += money * percent / 365;
    }
}
