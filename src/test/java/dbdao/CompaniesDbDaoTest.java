package dbdao;

import beans.Company;
import db.ConnectionPool;
import db.DbCompanyManager;
import db.DbManager;
import db.DbUtils;
import exceptions.LoginException;
import facade.CompanyFacade;
import junit.framework.JUnit4TestAdapter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompaniesDbDaoTest {
    private CompaniesDbDao companiesDbDao = new CompaniesDbDao();
    private CompanyFacade companyFacade = new CompanyFacade();
    private  Company company = new Company(0, "Faker", "Faker@gmail.com", "Fk123");

    @BeforeClass
    public static void CleanDb(){
        DbManager.dropCompleteDb();
        DbManager.buildCompleteDb();
    }

    @Test
    public void isCompanyExist() {
        companiesDbDao.addCompany(company);
        System.out.println("Checking company exist method.");
        assertTrue(companiesDbDao.isCompanyExist(company.getEmail(), company.getPassword()));
        System.out.println("Done.");
    }

    @Test
    public void addCompany() {
        System.out.println("Checking add company method.");
        assertTrue(companiesDbDao.addCompany(company));
        System.out.println("Done.");
    }

    @Test
    public void updateCompany() {
        System.out.println("Checking update company method.");
        assertTrue(companiesDbDao.updateCompany(company));
        System.out.println("Done.");
    }

    @Test
    public void deleteCompany() {
        System.out.println("Checking delete company method.");
        companiesDbDao.deleteCompany(company.getId());
        assertFalse(companiesDbDao.isCompanyExist(company.getEmail(), company.getPassword()));
        System.out.println("Done.");
    }

    @Test
    public void getAllCompanies() {
        System.out.println("Checking get all companies method.");
        Company test = new Company(0, "Who?", "Testing@walla.com", "Wh123");
        companiesDbDao.addCompany(test);
        assertEquals(2 , companiesDbDao.getAllCompanies().size());
        System.out.println("Done.");
    }

    @Test
    public void getOneCompany() {
        System.out.println("Checking get one company method.");
        Company test = new Company(0, "Who?", "Testing@walla.com", "Wh123");
        Company test2;
        companiesDbDao.addCompany(test);
        test.setId(companiesDbDao.getCompanyIdInLogin(test.getEmail(), test.getPassword()));
        assertEquals(test, test2 = companiesDbDao.getOneCompany(test.getId()));
        System.out.println("Done.");
    }

    @Test
    public void getCompanyIdInLogin() throws LoginException, SQLException {
        Company test = new Company(0, "Who?", "Testing@walla.com", "Wh123");
        companiesDbDao.addCompany(test);
        companyFacade.login(test.getEmail(), test.getPassword());
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, test.getEmail());
        values.put(2,test.getPassword());
        ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCompanyManager.GET_SINGLE_ID,values);
        assert resultSet != null;
        resultSet.next();
        assertEquals(companyFacade.getCompanyId(),resultSet.getInt("id"));
    }
    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(CompaniesDbDaoTest.class);
    }
}