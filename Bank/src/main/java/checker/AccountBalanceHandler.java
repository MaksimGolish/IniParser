package checker;

import model.TransferRequest;
import model.account.Account;

public class AccountBalanceHandler extends RequestHandler {
    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (account.getBalance() > request.getAmount() && account.withdrawalAvailable())
            return next(request, account);
        return false;
    }
}
