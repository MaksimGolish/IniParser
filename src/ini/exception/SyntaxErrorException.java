package ini.exception;

public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException() {
    }

    public SyntaxErrorException(String message) {
        super(message);
    }

    public SyntaxErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxErrorException(Throwable cause) {
        super(cause);
    }
}
