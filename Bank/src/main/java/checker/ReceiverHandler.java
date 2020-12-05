package checker;

import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import model.account.Account;
import transaction.Transfer;

public class ReceiverHandler extends RequestHandler {
    @Override
    public void handle(Transfer request, Account account) {
        if (TransactionProcessor.getInstance().findBankByAccount(request.getRecipient()) == null)
            throw new AccountNotFoundException(request.getRecipient());
        next(request, account);
    }
}
