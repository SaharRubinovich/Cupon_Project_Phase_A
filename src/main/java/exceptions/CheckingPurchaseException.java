package exceptions;

public class CheckingPurchaseException extends RuntimeException{
    public CheckingPurchaseException() {
        super(String.valueOf(ErrorMsg.CHECKING_PURCHASE));
    }

    public CheckingPurchaseException(String message) {
        super(message);
    }
}
