package checker;

import controller.TransactionProcessor;
import exception.AccountNotFoundException;
import transaction.TransferRequest;
import model.account.Account;

public class ReceiverHandler extends RequestHandler {
    @Override
    public boolean handle(TransferRequest request, Account account) {
        if (TransactionProcessor.getInstance().findBankByAccount(request.getRecipient()) == null)
            throw new AccountNotFoundException(request.getRecipient());
        else
            return next(request, account);
    }
}
