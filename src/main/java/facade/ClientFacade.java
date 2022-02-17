package facade;

import dao.CompaniesDao;
import dao.CouponsDao;
import dao.CustomersDao;
import dbdao.CompaniesDbDao;
import dbdao.CouponsDbDao;
import dbdao.CustomersDbDao;
import exceptions.LoginException;

public abstract class ClientFacade {
    protected CompaniesDao companiesDao;
    protected CustomersDao customersDao;
    protected CouponsDao couponsDao;
    protected CompaniesDbDao companiesDbDao;
    protected CustomersDbDao customersDbDao;
    protected CouponsDbDao couponsDbDao;

    public abstract boolean login(String email, String password) throws LoginException;
}
