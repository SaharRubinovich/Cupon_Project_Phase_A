package exceptions;

public class UpdateErrorException extends RuntimeException{
    public UpdateErrorException() {
        super(String.valueOf(ErrorMsg.UPDATE_ERROR));
    }

    public UpdateErrorException(String message) {
        super(message);
    }
}
