package exception;

public class IllegalLengthException extends RuntimeException {
    public IllegalLengthException() {
        super("Length can't be less than zero");
    }

    public IllegalLengthException(String message) {
        super(message);
    }
}
