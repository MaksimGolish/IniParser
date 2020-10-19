package exception;

public class ProductIsAlreadyRegisteredException extends RuntimeException {
    public ProductIsAlreadyRegisteredException(Long id) {
        super("Product " + id + " is already registered");
    }

    public ProductIsAlreadyRegisteredException(String message) {
        super(message);
    }

    public ProductIsAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIsAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
