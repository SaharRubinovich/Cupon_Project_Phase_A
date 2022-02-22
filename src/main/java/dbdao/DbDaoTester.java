package dbdao;

import beans.Company;
import db.DbManager;
import db.DbUtils;

import java.sql.SQLException;

public class DbDaoTester {
    public static void main(String[] args) throws SQLException, InterruptedException {
        /*
        DbManager.createDb();
        DbManager.createCategoriesTable();
        DbManager.createCompaniesTable();
        DbManager.createCustomersTable();
        DbManager.createCouponsTable();
        DbManager.createCustomersVsCouponsTable();
         */
        Company company = new Company(0,"testing","testing@email.com","Tt123");
        CompaniesDbDao companiesDbDao = new CompaniesDbDao();
        companiesDbDao.addCompany(company);


        /*
        DbManager.dropCustomersVsCouponsTable();
        DbManager.dropCouponsTable();
        DbManager.dropCompaniesTable();
        DbManager.dropCustomersTable();
        DbManager.dropCategoriesTable();
        DbManager.dropDb();
         */
    }
}
