package model.account;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.Client;
import transaction.Transfer;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class Account {
    protected final UUID id = UUID.randomUUID();
    protected double money;
    protected final Client owner;
    @Setter
    protected DateProvider dateProvider = LocalDateTime::now;

    public Account(double money, Client owner) {
        this.money = money;
        this.owner = owner;
    }

    public abstract void get(int amount);
    public abstract void add(int amount);
    public abstract double getBalance();
    public abstract boolean withdrawalAvailable();
    public void getForced(int amount) {
        money -= amount;
    }
    public void addForced(int amount) {
        money += amount;
    }

    public Transfer createTransferRequest(int amount, UUID receiver) {
        return new Transfer(amount, id, owner, receiver);
    }
}
