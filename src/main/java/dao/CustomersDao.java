package dao;

import beans.Customer;

import java.util.ArrayList;

public interface CustomersDao {
    boolean isCustomerExist(String email, String password);
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
    ArrayList<Customer> getAllCustomers();
    Customer getOneCustomer(int customerId);
}
