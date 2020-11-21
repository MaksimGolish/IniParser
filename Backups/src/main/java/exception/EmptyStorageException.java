package exception;

public class EmptyStorageException extends RuntimeException {
    public EmptyStorageException() {
        super("Cannot create point: no files");
    }

    public EmptyStorageException(String message) {
        super(message);
    }
}
