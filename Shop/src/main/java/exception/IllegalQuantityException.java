package exception;

public class IllegalQuantityException extends RuntimeException {
    public IllegalQuantityException() {
        super("Quantity can't be zero or less than a zero");
    }

    public IllegalQuantityException(String message) {
        super(message);
    }

    public IllegalQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalQuantityException(Throwable cause) {
        super(cause);
    }
}
