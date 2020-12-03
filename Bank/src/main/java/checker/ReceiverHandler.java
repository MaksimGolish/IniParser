package checker;

import controller.TransactionProcessor;
import transaction.TransferRequest;
import model.account.Account;

public class ReceiverHandler extends RequestHandler {
    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (TransactionProcessor.getInstance().findBankByAccount(request.getRecipient()) == null)
            return false;
        else
            return next(request, account);
    }
}
