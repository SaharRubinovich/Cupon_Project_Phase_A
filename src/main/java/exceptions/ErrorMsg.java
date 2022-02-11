package exceptions;

public enum ErrorMsg {
    ALREADY_EXIST("with the same name and email already exist"),
    UPDATE_ERROR("There was a error with the update you tried to do.");

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
