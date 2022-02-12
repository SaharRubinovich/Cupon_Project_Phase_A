package exceptions;

public class GetCouponException extends RuntimeException{
    public GetCouponException() {
        super(String.valueOf(ErrorMsg.GET_COUPON));
    }

    public GetCouponException(String message) {
        super(message);
    }
}
