package exception;

public class NoRegisteredRacersException extends RuntimeException {
    public NoRegisteredRacersException() {
        super("Race is not prepared");
    }

    public NoRegisteredRacersException(String message) {
        super(message);
    }
}
