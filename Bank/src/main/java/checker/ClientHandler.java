package checker;

import model.TransferRequest;
import model.account.Account;

public class ClientHandler extends RequestHandler {

    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (request.getSender().getPassport() == null && request.getAmount() > 50000)
            return false;
        else
            return next(request, account);
    }
}
