package exception;

public class IllegalMergeException extends RuntimeException {
    public IllegalMergeException() {
        super("Merge can't be performed for full restore points");
    }

    public IllegalMergeException(String message) {
        super(message);
    }
}
