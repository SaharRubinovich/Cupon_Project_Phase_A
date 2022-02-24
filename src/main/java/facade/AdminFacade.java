package facade;

import beans.Company;
import beans.Customer;
import db.DbUtils;
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
    /*
        Login method, uses the email and password to check if they are matching the admin info and return boolean.
        throw loginException if it's false.
     */

    public void addCompany(Company company) throws AdminFacadeException {
        if (companiesDbDao.isCompanyExist(company.getEmail(),company.getPassword())){
            throw new AdminFacadeException("Company "+ ErrorMsg.ALREADY_EXIST);
        } else {
            companiesDbDao.addCompany(company);
            company.setId(companiesDbDao.getCompanyIdInLogin(company.getEmail(), company.getPassword()));
        }
    }
    /*
        First of all check if there is already a company exist that matches the one we try to add.
       if not it will add it to the db and update the company id to match the db id.
     */

    public void updateCompany(Company company) throws AdminFacadeException {
        /*
        if (companiesDbDao.isCompanyExist(company.getPassword(), company.getPassword())){
            companiesDbDao.updateCompany(company);
            System.out.println("Company was updated!");
        } else {
            throw new AdminFacadeException(String.valueOf(ErrorMsg.UPDATE_ERROR));
        }
         */
        companiesDbDao.updateCompany(company);
        System.out.println("Company was updated!");
    }
    /*
        Simple method, updating a company in db.
     */

    public void deleteCompany(int companyId){
        companiesDbDao.deleteCompany(companyId);
    }
    /*
        Simple method, delete a company in db, there is no need to check anything because even if there
        is not a company that matches the id we send, the program will not fail.
     */

    public List<Company> getAllCompanies(){
        return companiesDbDao.getAllCompanies();
    }
    /*
        method that return a list of all the companies in the db.
     */

    public Company getOneCompany(int companyId){
        return companiesDbDao.getOneCompany(companyId);
    }
    /*
        method that give us a company instance from db that matches the id we gave.
     */

    public void addCustomer(Customer customer) throws AdminFacadeException, LoginException {
        if (customersDbDao.isCustomerExist(customer.getEmail(), customer.getPassword())){
            throw new AdminFacadeException("Customer " + ErrorMsg.ALREADY_EXIST);
        } else {
            customersDbDao.addCustomer(customer);
            customer.setId(customersDbDao.getCustomerIdInLogin(customer.getEmail(), customer.getPassword()));
        }
    }
    /*
        Method that check in the db if the customer we try to add already there, if not will add him and
        update the customer id to match the id he get in the db.
     */

    public void updateCustomer(Customer customer) throws AdminFacadeException {
        /*
        if (customersDbDao.isCustomerExist(customer.getEmail(), customer.getPassword())){
            customersDbDao.updateCustomer(customer);
            System.out.println("Customer was updated!");
        } else {
            throw new AdminFacadeException("Customer was not found.");
        }

         */
        customersDbDao.updateCustomer(customer);
        System.out.println("Customer was updated!");
    }
    /*
        update a customer in the db.
     */

    public void deleteCustomer(int customerId){
        customersDbDao.deleteCustomer(customerId);
    }
    /*
        Delete customer from db, no much to say.
     */

    public List<Customer> getAllCustomers(){
        return customersDbDao.getAllCustomers();
    }
    /*
        Method that give us a list of all the customer in the db
     */

    public Customer getOneCustomer(int customerId){
        return customersDbDao.getOneCustomer(customerId);
    }
    /*
        Method that give us a Customer instance that matches the id we requested from the db.
     */
}
