package exception;

public class PointCannotBeDeletedException extends RuntimeException {
    public PointCannotBeDeletedException() {
    }

    public PointCannotBeDeletedException(String message) {
        super(message);
    }
}
