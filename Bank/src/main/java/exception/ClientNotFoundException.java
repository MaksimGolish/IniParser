package exception;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(UUID id) {
        super("Client " + id + " not found");
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}
