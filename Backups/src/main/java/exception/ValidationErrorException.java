package exception;

public class ValidationErrorException extends RuntimeException {
    public ValidationErrorException() {
    }

    public ValidationErrorException(String message) {
        super(message);
    }
}
