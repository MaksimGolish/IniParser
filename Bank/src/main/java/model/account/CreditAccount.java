package model.account;

import exception.WithdrawalNotAvailableException;
import lombok.Builder;
import model.Client;

import java.time.Duration;
import java.time.LocalDateTime;

public class CreditAccount extends Account {
    private LocalDateTime taxablePeriod;
    private final double percent;
    private final double limit;

    @Builder
    public CreditAccount(Client owner, double percent, double money) {
        super(money, owner);
        this.percent = percent;
        this.limit = money;
    }

    @Override
    public void get(int amount) {
        recalculate();
        if (money < amount)
            throw new WithdrawalNotAvailableException();
        money -= amount;
        if (money < limit)
            taxablePeriod = dateProvider.now();
    }

    @Override
    public void add(int amount) {
        recalculate();
        money += amount;
        if (money >= limit)
            taxablePeriod = null;
    }

    @Override
    public double getBalance() {
        recalculate();
        return money;
    }

    @Override
    public boolean withdrawalAvailable() {
        return money > 0;
    }

    private void recalculate() {
        if (taxablePeriod == null)
            return;
        long days = Duration.between(taxablePeriod, dateProvider.now()).toDays();
        if (days == 0)
            return;
        for (int i = 0; i < days; i++)
            money -= money * percent / 365 / 100;
        taxablePeriod = dateProvider.now();
    }
}
