package exception;

public class NotEnoughProductsException extends RuntimeException {
    public NotEnoughProductsException() {
        super("Not enough products in the shop");
    }

    public NotEnoughProductsException(String message) {
        super(message);
    }

    public NotEnoughProductsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughProductsException(Throwable cause) {
        super(cause);
    }
}
