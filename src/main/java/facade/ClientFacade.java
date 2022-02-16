package facade;

import dao.CompaniesDao;
import dao.CouponsDao;
import dao.CustomersDao;
import exceptions.LoginException;

public abstract class ClientFacade {
    protected CompaniesDao companiesDao;
    protected CustomersDao customersDao;
    protected CouponsDao couponsDao;

    public abstract boolean login(String email, String password) throws LoginException;
}
