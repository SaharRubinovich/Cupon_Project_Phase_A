package exceptions;

public class CustomerFacadeException extends Exception{
    public CustomerFacadeException() {
        super("There was a error in the customer facade class");
    }

    public CustomerFacadeException(String message) {
        super(message);
    }
}
