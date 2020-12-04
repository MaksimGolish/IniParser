package exception;

public class TransferException extends RuntimeException {
    public TransferException(){
    }

    public TransferException(String message) {
        super(message);
    }
}
