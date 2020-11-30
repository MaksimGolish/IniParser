package checker;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import model.TransferRequest;
import model.account.Account;

@RequiredArgsConstructor
public abstract class RequestHandler {
    protected RequestHandler next;

    public RequestHandler setNext(RequestHandler next) {
        this.next = next;
        return next;
    }

    public abstract boolean handle(TransferRequest request, Account account);
    public boolean next(TransferRequest request, Account account) {
        return next == null || next.handle(request, account);
    }
}
