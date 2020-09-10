package ini.exception;

public class WrongFileExtensionException extends RuntimeException {
    public WrongFileExtensionException() {
    }

    public WrongFileExtensionException(String message) {
        super(message);
    }

    public WrongFileExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongFileExtensionException(Throwable cause) {
        super(cause);
    }
}
