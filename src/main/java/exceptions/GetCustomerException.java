package exceptions;

public class GetCustomerException extends RuntimeException{
    public GetCustomerException() {
        super(String.valueOf(ErrorMsg.GET_CUSTOMER));
    }

    public GetCustomerException(String message) {
        super(message);
    }
}
