package model.account;

import exception.WithdrawalNotAvailableException;
import lombok.RequiredArgsConstructor;
import model.Client;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditAccount extends Account {
    private LocalDateTime taxablePeriod;
    private final double percent;
    private final int limit;

    public CreditAccount(Client owner, double percent, int money) {
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
            taxablePeriod = LocalDate.now().atStartOfDay();
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
        long days = Duration.between(taxablePeriod, LocalDateTime.now()).toDays();
        if (days == 0)
            return;
        for (int i = 0; i < days; i++)
            money -= money * percent / 365 / 100;
        taxablePeriod = LocalDate.now().atTime(0, 0);
    }
}
