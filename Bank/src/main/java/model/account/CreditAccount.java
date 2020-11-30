package model.account;

import model.Client;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class CreditAccount extends Account {
    private LocalDateTime taxablePeriod;
    private final double percent;

    public CreditAccount(Client owner, double percent) {
        super(0, owner);
        this.percent = percent;
    }

    @Override
    public void get(int amount) {
        if (money >= 0 && money - amount < 0)
            taxablePeriod = LocalDateTime.now();
        money -= amount;
    }

    @Override
    public void add(int amount) {
        count();
        money += amount;
        if (money > 0)
            taxablePeriod = null;
    }

    @Override
    public double getBalance() {
        count();
        return money;
    }

    @Override
    public boolean withdrawalAvailable() {
        return true;
    }

    private void count() {
        if (taxablePeriod != null && money < 0)
            money -= money * percent / 365 * Duration.between(taxablePeriod, LocalDateTime.now()).toDays();
    }
}
