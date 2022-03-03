package facade;

import beans.Company;
import db.ConnectionPool;
import dbdao.CompaniesDbDao;
import dbdao.CustomersDbDaoTest;
import junit.framework.JUnit4TestAdapter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class CompanyFacadeTest {
    private static ConnectionPool connectionPool;
    private static Company companyTest = new Company(0,"CompanyTest","JunitTest@test.com","1234");
    private static AdminFacade adminFacade = new AdminFacade();
    private static CompanyFacade companyFacade = new CompanyFacade();
    private static CompaniesDbDao companiesDbDao = new CompaniesDbDao();

    @BeforeClass
    public static void init(){

    }

    @Test
    public void login() {
    }

    @Test
    public void addCoupon() {
    }

    @Test
    public void updateCoupon() {
    }

    @Test
    public void deleteCoupon() {
    }

    @Test
    public void getCompanyCoupons() {
    }

    @Test
    public void testGetCompanyCoupons() {
    }

    @Test
    public void testGetCompanyCoupons1() {
    }

    @Test
    public void getCompanyDetails() {
    }

    @AfterClass
    public static void end() throws SQLException, InterruptedException {
        ConnectionPool.getInstance().closeConnection();
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CustomersDbDaoTest.class);
    }
}