package checker;

import controller.TransactionProcessor;
import exception.TransferException;
import model.account.Account;
import transaction.Transfer;

public class TransactionHandler extends RequestHandler {
    @Override
    public void handle(Transfer request, Account account) {
        if (TransactionProcessor.getInstance().proceedTransaction(request))
            next(request, account);
        throw new TransferException("Cannot perform transfer");
    }
}
