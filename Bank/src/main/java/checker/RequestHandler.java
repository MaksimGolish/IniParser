package checker;

import lombok.RequiredArgsConstructor;
import model.account.Account;
import transaction.Transfer;

@RequiredArgsConstructor
public abstract class RequestHandler {
    protected RequestHandler next;

    public RequestHandler setNext(RequestHandler next) {
        this.next = next;
        return this.next;
    }

    public abstract void handle(Transfer request, Account account);
    public void next(Transfer request, Account account) {
        if (next == null)
            return;
        next.handle(request, account);
    }
}
