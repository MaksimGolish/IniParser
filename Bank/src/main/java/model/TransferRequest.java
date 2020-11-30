package model;

import controller.TransactionProcessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class TransferRequest {
    private final UUID id = UUID.randomUUID();
    private final Date time = new Date();

    private final int amount;
    private final UUID senderAccount;
    private final Client sender;
    private final UUID recipient;

    public final void cancel() {
        TransactionProcessor
                .getInstance()
                .cancel(this);
    }
}
