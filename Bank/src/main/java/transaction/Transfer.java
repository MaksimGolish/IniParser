package transaction;

import controller.TransactionProcessor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import model.Client;

import java.util.Date;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Transfer implements Transaction {
    private final UUID id = UUID.randomUUID();
    private final Date time = new Date();

    private final int amount;
    private final UUID senderAccount;
    private final Client sender;
    private final UUID recipient;

    @Override
    public void cancel() {
        TransactionProcessor
                .getInstance()
                .cancelTransfer(this);
    }
}
