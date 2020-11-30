package model.account;

import controller.TransactionProcessor;
import lombok.Data;
import model.Client;
import model.TransferRequest;

import java.util.UUID;

@Data
public abstract class Account {
    protected final UUID id = UUID.randomUUID();
    protected double money;
    protected final Client owner;
    protected boolean verified = false;

    public Account(int money, Client owner) {
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

    public TransferRequest createTransferRequest(int amount, UUID receiver) {
        return new TransferRequest(amount, id, owner, receiver);
    }
}
