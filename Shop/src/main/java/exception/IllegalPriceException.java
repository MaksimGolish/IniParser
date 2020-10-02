package exception;

public class IllegalPriceException extends RuntimeException {
    public IllegalPriceException() {
        super("Price can't be less than zero");
    }

    public IllegalPriceException(String message) {
        super(message);
    }

    public IllegalPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPriceException(Throwable cause) {
        super(cause);
    }
}
