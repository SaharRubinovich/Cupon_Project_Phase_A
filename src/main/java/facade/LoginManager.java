package facade;

import beans.Company;
import exceptions.LoginException;

public class LoginManager {
    private static LoginManager instance;

    private LoginManager() {
        instance = new LoginManager();
    }

    ;

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if ((instance == null)) {
                    instance = new LoginManager();
                }
            }
            return instance;
        } else {
            return instance;
        }
    }

    public static ClientFacade login(String email, String password, ClientType clientType) {
        ClientFacade clientFacade = null;
        getInstance();
        try {
            if (clientType.equals(ClientType.Administrator)) {
                AdminFacade adminFacade = new AdminFacade();
                if (adminFacade.login(email, password)) {
                    return clientFacade = adminFacade;
                }
            } else if (clientType.equals(ClientType.Company)) {
                CompanyFacade companyFacade = new CompanyFacade();
                if (companyFacade.login(email, password)) {
                    return clientFacade = companyFacade;
                }
            } else if (clientType.equals(ClientType.Customer)) {
                CustomerFacade customerFacade = new CustomerFacade();
                if (customerFacade.login(email, password)) {
                    return clientFacade = customerFacade;
                }
            }
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
        return clientFacade;
    }

}
