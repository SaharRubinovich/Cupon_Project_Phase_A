package facade;

import beans.Company;
import beans.Customer;
import exceptions.AdminFacadeException;
import exceptions.ErrorMsg;
import exceptions.LoginException;

import java.util.List;

public class AdminFacade extends ClientFacade{
    @Override
    public boolean login(String email, String password) throws LoginException {
        if(email.equals("admin@admin.com") && password.equals("admin") ) {
            System.out.println("Logged in successfully!");
            return true;
        } else {
            throw new LoginException();
        }
    }

    public void addCompany(Company company) throws AdminFacadeException {
        if (companiesDbDao.isCompanyExist(company.getEmail(),company.getPassword())){
            throw new AdminFacadeException("Company "+ ErrorMsg.ALREADY_EXIST);
        }
    }

    public void updateCompany(Company company) throws AdminFacadeException {
        if (companiesDbDao.isCompanyExist(company.getPassword(), company.getPassword())){
            companiesDbDao.updateCompany(company);
            System.out.println("Company was updated!");
        } else {
            throw new AdminFacadeException(String.valueOf(ErrorMsg.UPDATE_ERROR));
        }
    }

    public void deleteCompany(int companyId){
        companiesDbDao.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies(){
        return companiesDbDao.getAllCompanies();
    }

    public Company getOneCompany(int companyId){
        return companiesDbDao.getOneCompany(companyId);
    }

    public void addCustomer(Customer customer) throws AdminFacadeException {
        if (customersDbDao.isCustomerExist(customer.getEmail(), customer.getPassword())){
            throw new AdminFacadeException("Customer " + ErrorMsg.ALREADY_EXIST);
        }
    }

    public void updateCustomer(Customer customer) throws AdminFacadeException {
        if (customersDbDao.isCustomerExist(customer.getEmail(), customer.getPassword())){
            customersDbDao.updateCustomer(customer);
            System.out.println("Customer was updated!");
        } else {
            throw new AdminFacadeException("Customer was not found.");
        }
    }

    public void deleteCustomer(int customerId){
        customersDbDao.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers(){
        return getAllCustomers();
    }

    public Customer getOneCustomer(int customerId){
        return customersDbDao.getOneCustomer(customerId);
    }
}
