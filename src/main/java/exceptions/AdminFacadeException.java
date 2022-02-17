package exceptions;

public class AdminFacadeException extends Exception{
    public AdminFacadeException() {
        super("Error accord in admin facade");
    }

    public AdminFacadeException(String message) {
        super(message);
    }
}
