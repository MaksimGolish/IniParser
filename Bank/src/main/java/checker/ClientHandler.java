package checker;

import exception.UnverifiedTransferException;
import lombok.AllArgsConstructor;
import transaction.TransferRequest;
import model.account.Account;

@AllArgsConstructor
public class ClientHandler extends RequestHandler {
    private final double limit;

    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (request.getSender().getPassport() == null && request.getAmount() > limit)
            throw new UnverifiedTransferException();
        else
            return next(request, account);
    }
}
