package exceptions;

public class BankAppException extends RuntimeException {
    public BankAppException(String message, Throwable e) {
        super(message, e);
    }
}
