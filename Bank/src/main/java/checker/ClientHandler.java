package checker;

import exception.UnverifiedTransferException;
import lombok.AllArgsConstructor;
import model.account.Account;
import transaction.Transfer;

@AllArgsConstructor
public class ClientHandler extends RequestHandler {
    private final double limit;

    @Override
    public boolean handle(Transfer request, Account account) {
        if (request.getSender().getPassport() == null && request.getAmount() > limit)
            throw new UnverifiedTransferException();
        else
            return next(request, account);
    }
}
