package exception;

public class IllegalPointTransformationException extends RuntimeException {
    public IllegalPointTransformationException() {
        super("Merge can't be performed for full restore points");
    }

    public IllegalPointTransformationException(String message) {
        super(message);
    }
}
