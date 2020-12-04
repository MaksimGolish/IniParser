package checker;

import controller.TransactionProcessor;
import exception.TransferException;
import transaction.TransferRequest;
import model.account.Account;

public class TransactionHandler extends RequestHandler {
    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (TransactionProcessor.getInstance().proceedTransaction(request))
            return next(request, account);
        throw new TransferException("Cannot perform transfer");
    }
}
