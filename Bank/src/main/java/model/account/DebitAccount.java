package model.account;

import exception.IllegalRequestException;
import exception.NotEnoughMoneyException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.Client;

import java.util.UUID;

public class DebitAccount extends Account {
    @Getter
    @Setter
    private double percent;
    private double bonus = 0;

    @Builder
    private DebitAccount(Client owner, double percent) {
        super(0, owner);
        this.percent = percent;
    }

    @Override
    public void get(int amount) {
        if (money - amount < 0)
            throw new NotEnoughMoneyException();
        if (amount <= 0)
            throw new IllegalRequestException();
        money -= amount;
    }

    @Override
    public void add(int amount) {
        if (amount <= 0)
            throw new IllegalRequestException();
        money += amount;
    }

    @Override
    public double getBalance() {
        return money;
    }

    @Override
    public boolean withdrawalAvailable() {
        return true;
    }

    public void updateBonus() {
        bonus += money * percent / 365 / 100;
    }

    public void update() {
        money += bonus;
        bonus = 0;
    }
}
