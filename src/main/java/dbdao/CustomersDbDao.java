package dbdao;

import beans.Customer;
import dao.CustomersDao;
import db.ConnectionPool;
import db.DbManager;
import db.DbUtils;
import exceptions.CustomerAlreadyExistException;
import exceptions.GetCustomerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomersDbDao implements CustomersDao {
    private ConnectionPool connectionPool;

    @Override
    public boolean isCustomerExist(String email, String password) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        ResultSet resultSet = null;
        try {
            resultSet = DbUtils.runQueryWithResultSet(DbManager.IS_CUSTOMER_EXIST, values);
        } catch (SQLException | InterruptedException e) {
            throw new CustomerAlreadyExistException();
        }
        if (resultSet != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addCustomer(Customer customer) {
        if (!isCustomerExist(customer.getEmail(), customer.getPassword())) {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, customer.getFirstName());
            values.put(2, customer.getLastName());
            values.put(3, customer.getEmail());
            values.put(4, customer.getPassword());
            return DbUtils.runQuery(DbManager.CREATE_CUSTOMER, values);
        } else {
            System.out.println("Customer already exist");
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customer.getFirstName());
        values.put(2, customer.getLastName());
        values.put(3, customer.getEmail());
        values.put(4, customer.getId());
        return DbUtils.runQuery(DbManager.UPDATE_CUSTOMER, values);
    }

    @Override
    public void deleteCustomer(int customerId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customerId);
        DbUtils.runQuery(DbManager.DELETE_CUSTOMER, values);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        try {
            ResultSet resultSet = DbUtils.getAllInstancesWithResultSet(DbManager.GET_ALL_CUSTOMERS);
            while (resultSet.next()) {
                allCustomers.add(buildCustomer(resultSet));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    @Override
    public Customer getOneCustomer(int customerId) {
        Customer customer = null;
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customerId);
        try {
        ResultSet resultSet = DbUtils.runQueryWithResultSet(DbManager.GET_SINGLE_CUSTOMER, values);
        customer = buildCustomer(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new GetCustomerException();
        }
        return customer;
    }

    private Customer buildCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
