package exception;

public class IllegalRequestException extends RuntimeException {
    public IllegalRequestException() {
    }

    public IllegalRequestException(String message) {
        super(message);
    }
}
