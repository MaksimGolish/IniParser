package checker;

import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import model.account.Account;
import transaction.Transfer;

public class ReceiverHandler extends RequestHandler {
    @Override
    public boolean handle(Transfer request, Account account) {
        if (TransactionProcessor.getInstance().findBankByAccount(request.getRecipient()) == null)
            throw new AccountNotFoundException(request.getRecipient());
        else
            return next(request, account);
    }
}
