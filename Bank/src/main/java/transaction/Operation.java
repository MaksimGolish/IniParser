package transaction;

import controller.TransactionProcessor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Operation implements Transaction {
    private final UUID id = UUID.randomUUID();
    private final Date date = new Date();
    private final UUID account;
    private final OperationType type;
    private final int amount;

    @Override
    public void cancel() {
        TransactionProcessor
                .getInstance()
                .cancelInnerOperation(this);
    }
}
