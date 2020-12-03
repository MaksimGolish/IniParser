package exception;

public class IllegalPercentException extends IllegalArgumentException {
    public IllegalPercentException() {
        super("Percent can't be less than 0");
    }
}
