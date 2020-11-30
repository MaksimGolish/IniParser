package service;

import exception.ClientNotFoundException;
import lombok.Getter;
import model.Client;
import model.account.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankService {
    @Getter
    private final Map<UUID, Account> accounts;
    private final Map<UUID, Client> clients;

    public BankService() {
        accounts = new HashMap<>();
        clients = new HashMap<>();
    }

    public void addAccount(Account account) {
        if (!clients.containsKey(account.getOwner().getId()))
            throw new ClientNotFoundException(account.getOwner().getId());
        account.setVerified(clients.get(account.getOwner().getId()).getPassport() != null);
        accounts.put(account.getId(), account);
    }

    public void addClient(Client client) {
        clients.put(client.getId(), client);
    }

    public Client getClient(UUID id) {
        return clients.get(id);
    }

    public Account getAccount(UUID id) {
        return accounts.get(id);
    }

    public boolean hasAccount(UUID id) {
        return accounts.containsKey(id);
    }
}
