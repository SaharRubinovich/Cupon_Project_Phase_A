package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.ConnectionPool;
import db.DbManager;
import dbdao.CompaniesDbDao;
import dbdao.CouponsDbDao;
import dbdao.CustomersDbDao;
import dbdao.CustomersDbDaoTest;
import exceptions.AdminFacadeException;
import exceptions.CompanyFacadeException;
import exceptions.CustomerFacadeException;
import exceptions.LoginException;
import junit.framework.JUnit4TestAdapter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CustomerFacadeTest {
    private static ConnectionPool connectionPool;
    private static Customer customerTest = new Customer(0, "Junit", "Test", "JunitCustomer@test.com", "1234");
    private static Coupon couponTest = new Coupon(0, 0, 3, "JunitTest", "Testing",
            Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(12)), 30, 22.5, "None");
    private static Company company = new Company(0, "Junit", "Testing1@test.com", "12345");
    private static AdminFacade adminFacade = new AdminFacade();
    private static CompanyFacade companyFacade = new CompanyFacade();
    private static CompaniesDbDao companiesDbDao = new CompaniesDbDao();
    private static CustomerFacade customerFacade = new CustomerFacade();
    private static CustomersDbDao customersDbDao = new CustomersDbDao();
    private static CouponsDbDao couponsDbDao = new CouponsDbDao();

    @BeforeClass
    public static void init() throws SQLException, AdminFacadeException, LoginException, CompanyFacadeException {
        connectionPool = ConnectionPool.getInstance();
        adminFacade.addCustomer(customerTest);
        customerFacade.setCustomerId(customerTest.getId());
        customerTest.setId(customersDbDao.getCustomerIdInLogin(customerTest.getEmail(), customerTest.getPassword()));
        adminFacade.addCompany(company);
        company.setId(companiesDbDao.getCompanyIdInLogin(company.getEmail(), company.getPassword()));
        companyFacade.setCompanyId(company.getId());
        couponTest.setCompanyId(company.getId());
        companyFacade.addCoupon(couponTest);
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, company.getId());
        values.put(2, couponTest.getTitle());
        couponTest.setId(couponsDbDao.getCouponId(values));
    }

    @Test
    public void login() throws LoginException {
        assertTrue(customerFacade.login(customerTest.getEmail(), customerTest.getPassword()));
    }

    @Test
    public void purchaseCoupon() throws CustomerFacadeException {
        customerFacade.purchaseCoupon(couponTest);
        assertTrue(customersDbDao.checkPurchase(customerTest.getId(), couponTest.getId()));
    }

    @Test
    public void getCustomerCoupons() throws CustomerFacadeException {
        assertEquals(1, customerFacade.getCustomerCoupons().size());
    }

    @Test
    public void testGetCustomerCoupons() {
        assertEquals(0, customerFacade.getCustomerCoupons(10).size());
    }

    @Test
    public void testGetCustomerCoupons1() throws CustomerFacadeException {
        assertEquals(0, customerFacade.getCustomerCoupons(Category.RESTAURANT).size());
    }

    @Test
    public void getCustomerDetails() {
        Customer test = customerFacade.getCustomerDetails();
        assertEquals(test, customerTest);
    }

    @AfterClass
    public static void close() throws SQLException, InterruptedException {
        DbManager.dropCompleteDb();
        ConnectionPool.getInstance().closeConnection();
    }
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CustomersDbDaoTest.class);
    }
}