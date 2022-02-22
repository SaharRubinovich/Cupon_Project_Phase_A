package dbdao;

import beans.Customer;
import exceptions.LoginException;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomersDbDaoTest {
    private CustomersDbDao customersDbDao = new CustomersDbDao();
    Customer test = new Customer(0, "Test", "Tester", "Testing@email.com", "1234");
    Customer customer = new Customer(0, "Sahar", "Rubinovich", "sahar2613@gmail.com", "1234567");

    @Test
    public void isCustomerExist() {
        boolean test = customersDbDao.isCustomerExist(customer.getEmail(), customer.getPassword());
        assertTrue(test);
    }

    @Test
    public void addCustomer() {
        assertTrue(customersDbDao.addCustomer(test));
    }

    @Test
    public void updateCustomer() {
        test.setEmail("newEmail@email.com");
        assertTrue(customersDbDao.updateCustomer(test));
    }

    @Test
    public void deleteCustomer() throws LoginException {
        test.setId(customersDbDao.getCustomerIdInLogin(test.getEmail(), test.getPassword()));
        customersDbDao.deleteCustomer(test.getId());
        assertFalse(customersDbDao.isCustomerExist(test.getEmail(), test.getPassword()));
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customerList = customersDbDao.getAllCustomers();
        assertEquals(3, customerList.size());
    }

    @Test
    public void getOneCustomer() throws LoginException {
        customer.setId(customersDbDao.getCustomerIdInLogin(customer.getEmail(), customer.getPassword()));
        Customer test2 = customersDbDao.getOneCustomer(customer.getId());
        assertEquals(customer, test2);
    }

    @Test
    public void checkPurchase() {
        assertTrue(customersDbDao.checkPurchase(1, 1));
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CustomersDbDaoTest.class);
    }
}