package exceptions;

public class LoginException extends Exception{
    public LoginException() {
        super(String.valueOf(ErrorMsg.LOGIN_ERROR));
    }

    public LoginException(String message) {
        super(message);
    }
}
