package checker;

import controller.TransactionProcessor;
import exception.TransferException;
import model.account.Account;
import transaction.Transfer;

public class TransactionHandler extends RequestHandler {
    @Override
    public boolean handle(Transfer request, Account account) {
        if (TransactionProcessor.getInstance().proceedTransaction(request))
            return next(request, account);
        throw new TransferException("Cannot perform transfer");
    }
}
