package facade;

public class LoginManager {
    private static LoginManager instance;

    private LoginManager(){
        instance = new LoginManager();
    };

    public static LoginManager getInstance(){
        if (instance==null){
            synchronized (LoginManager.class){
                if ((instance==null)){
                    instance = new LoginManager();
                }
            }
            return instance;
        } else {
            return instance;
        }
    }
}
