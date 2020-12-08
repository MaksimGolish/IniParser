package model.account;

import exception.IllegalRequestException;
import exception.NotEnoughMoneyException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.Client;

import java.time.Duration;
import java.time.LocalDateTime;

public class DebitAccount extends Account {
    @Getter
    @Setter
    private double percent;
    private double bonus = 0;
    private LocalDateTime time = dateProvider.now();
    private int daysCounter = 0;

    @Builder
    private DebitAccount(Client owner, double percent) {
        super(0, owner);
        this.percent = percent;
    }

    @Override
    public void get(int amount) {
        recalculate();
        if (money - amount < 0)
            throw new NotEnoughMoneyException();
        if (amount <= 0)
            throw new IllegalRequestException();
        money -= amount;
    }

    @Override
    public void add(int amount) {
        recalculate();
        if (amount <= 0)
            throw new IllegalRequestException();
        money += amount;
    }

    @Override
    public double getBalance() {
        recalculate();
        return money;
    }

    @Override
    public boolean withdrawalAvailable() {
        return true;
    }

    private void recalculate() {
        long days = Duration.between(time, dateProvider.now()).toDays();
        if (days == 0)
            return;
        for (int i = 0; i < days; i++)
            bonus += (money + bonus) * percent / 365 / 100;
        time = dateProvider.now();
        daysCounter += days;
        if (daysCounter >= 30) {
            money += bonus;
            bonus = 0;
            daysCounter = daysCounter - 30;
        }
    }
}
