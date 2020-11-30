package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Transaction {
    private final UUID id = UUID.randomUUID();
    private final Date time = new Date();

    private final int amount;
    private final UUID sender;
    private final UUID recipient;

    public Transaction(int amount, UUID sender, UUID recipient) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }
}
