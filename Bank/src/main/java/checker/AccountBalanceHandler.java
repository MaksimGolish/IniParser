package checker;

import exception.NotEnoughMoneyException;
import transaction.TransferRequest;
import model.account.Account;

public class AccountBalanceHandler extends RequestHandler {
    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (account.getBalance() > request.getAmount() && account.withdrawalAvailable()) {
            account.get(request.getAmount());
            return next(request, account);
        }
        throw new NotEnoughMoneyException();
    }
}
