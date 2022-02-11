package exceptions;

public class CompanyAlreadyExistException extends Exception{
    public CompanyAlreadyExistException() {
        super(String.valueOf(ErrorMsg.ALREADY_EXIST));
    }

    public CompanyAlreadyExistException(String message) {
        super(message);
    }
}
