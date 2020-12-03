package model.account;

import exception.WithdrawalNotAvailableException;
import lombok.Builder;
import model.Client;

import java.time.LocalDateTime;

public class DepositAccount extends Account {
    private final LocalDateTime expirationTime;
    private final double percent;
    private boolean isActual = false;

    @Builder
    public DepositAccount(double money, Client owner, LocalDateTime expirationTime) {
        super(money, owner);
        this.expirationTime = expirationTime;
        if (money < 50000)
            percent = 3;
        else if (money >= 50000 && money < 100000)
            percent = 3.5;
        else
            percent = 4;
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
        return expirationTime.isBefore(dateProvider.now());
    }

    public void recalculate() {
        if (!isActual)
            money += money * percent / 365;
        isActual = true;
    }
}
