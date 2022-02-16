package exceptions;

public class CompanyFacadeException extends Exception{
    public CompanyFacadeException() {
        super("There was error in Company Facade Class");
    }

    public CompanyFacadeException(String message) {
        super(message);
    }
}
