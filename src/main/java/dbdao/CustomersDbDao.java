package dbdao;

import beans.Coupon;
import beans.Customer;
import dao.CustomersDao;
import db.ConnectionPool;
import db.DbCouponManager;
import db.DbCustomerManager;
import db.DbUtils;
import exceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDbDao implements CustomersDao {
    private ConnectionPool connectionPool;

    @Override
    public boolean isCustomerExist(String email, String password) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        ResultSet resultSet = null;
        resultSet = DbUtils.runQueryWithResultSet(DbCustomerManager.IS_CUSTOMER_EXIST, values);
        try {
            assert resultSet != null;
            resultSet.next();
            if (resultSet.getInt("counter") > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        if (!isCustomerExist(customer.getEmail(), customer.getPassword())) {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, customer.getFirstName());
            values.put(2, customer.getLastName());
            values.put(3, customer.getEmail());
            values.put(4, customer.getPassword());
            return DbUtils.runQuery(DbCustomerManager.CREATE_CUSTOMER, values);
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
        return DbUtils.runQuery(DbCustomerManager.UPDATE_CUSTOMER, values);
    }

    @Override
    public void deleteCustomer(int customerId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customerId);
        DbUtils.runQuery(DbCustomerManager.DELETE_CUSTOMER, values);
    }

    @Override
    public List<Customer> getAllCustomers() {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        try {
            ResultSet resultSet = DbUtils.getAllInstancesWithResultSet(DbCustomerManager.GET_ALL_CUSTOMERS);
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
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCustomerManager.GET_SINGLE_CUSTOMER, values);
            assert resultSet != null;
            resultSet.next();
            customer = buildCustomer(resultSet);
        } catch (SQLException e) {
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

    public boolean checkPurchase(int customerId, int couponId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customerId);
        values.put(2, couponId);
        ResultSet resultSet = null;
        try {
            resultSet = DbUtils.runQueryWithResultSet(DbCustomerManager.CHECK_IF_BOUGHT_COUPON, values);
            assert resultSet != null;
            resultSet.next();
            if (resultSet.getInt("counter") > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throw new CheckingPurchaseException();
        }
        return false;
    }

    public int getCustomerIdInLogin(String email, String password) throws LoginException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCustomerManager.GET_SINGLE_CUSTOMER_ID, values);
            assert resultSet != null;
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new LoginException("Error accord while fetching customer id.");
        }
    }
}
