package exceptions;

public class GetCompanyException extends RuntimeException{
    public GetCompanyException() {
        super(String.valueOf(ErrorMsg.GET_COMPANY));
    }

    public GetCompanyException(String message) {
        super(message);
    }
}
