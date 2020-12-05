package checker;

import exception.UnverifiedTransferException;
import lombok.AllArgsConstructor;
import model.account.Account;
import transaction.Transfer;

@AllArgsConstructor
public class ClientHandler extends RequestHandler {
    private final double limit;

    @Override
    public void handle(Transfer request, Account account) {
        if (!request.getSender().isVerified() && request.getAmount() > limit)
            throw new UnverifiedTransferException();
        next(request, account);
    }
}
