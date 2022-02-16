package dbdao;

import db.DbManager;

import java.sql.SQLException;

public class DbDaoTester {
    public static void main(String[] args) throws SQLException, InterruptedException {

        DbManager.createDb();
        DbManager.createCategoriesTable();
        DbManager.createCompaniesTable();
        DbManager.createCustomersTable();
        DbManager.createCouponsTable();
        DbManager.createCustomersVsCouponsTable();


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
