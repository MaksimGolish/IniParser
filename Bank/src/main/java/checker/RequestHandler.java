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

    public abstract boolean handle(Transfer request, Account account);
    public boolean next(Transfer request, Account account) {
        return next == null || next.handle(request, account);
    }
}
