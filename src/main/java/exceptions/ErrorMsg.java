package exceptions;

public enum ErrorMsg {
    ALREADY_EXIST("with the same name and email already exist"),
    UPDATE_ERROR("There was a error with the update you tried to do."),
    GET_COMPANY("There was an error while trying to get company instance"),
    GET_COUPON("There was an error while trying to get coupon instance"),
    GET_CUSTOMER("There was an error while trying to get customer instance"),
    CHECKING_PURCHASE("There was an error while trying to check your purchase"),
    LOGIN_ERROR("The was an error in the login attempt");

    private String msg;

    ErrorMsg(String msg) {
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
