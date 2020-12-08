package exception;

public class WithdrawalNotAvailableException extends RuntimeException {
    public WithdrawalNotAvailableException() {
    }

    public WithdrawalNotAvailableException(String message) {
        super(message);
    }
}
