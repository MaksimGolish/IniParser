package exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(UUID id) {
        super("Account " + id + " not found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
