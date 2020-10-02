package ini.exception;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException() {
    }

    public SectionNotFoundException(String message) {
        super(message);
    }

    public SectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SectionNotFoundException(Throwable cause) {
        super(cause);
    }
}
