package checker;

import controller.TransactionProcessor;
import transaction.TransferRequest;
import model.account.Account;

public class TransactionHandler extends RequestHandler {
    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (TransactionProcessor.getInstance().proceedTransaction(request))
            return next(request, account);
        return false;
    }
}
