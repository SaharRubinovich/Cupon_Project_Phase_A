package exceptions;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException() {
        super("Customer " + String.valueOf(ErrorMsg.ALREADY_EXIST));
    }

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
