package exception;

public class UnverifiedTransferException extends RuntimeException {
    public UnverifiedTransferException() {
        super("Cannot create transfer: passport is not defined");
    }

    public UnverifiedTransferException(String message) {
        super(message);
    }
}
