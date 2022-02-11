package dbdao;

import beans.Company;
import dao.CompaniesDao;
import db.ConnectionPool;
import db.DbUtils;
import db.DbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompaniesDbDao implements CompaniesDao {
    private ConnectionPool connectionPool;

    //methods
    @Override
    public boolean isCompanyExist(String email, String password) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        ResultSet resultSet = DbUtils.runQueryWithResultSet(DbManager.IS_COMPANY_EXIST, values);
        if (resultSet != null) {
            return true;
        } else {
            return false;
        }
    }
    /*
        Method to check if the company already exist in DB with map and two string values.
        boolean method so I used it in addCompany method to check if the company exist before creating
        a new 1.
     */

    @Override
    public boolean addCompany(Company company) {
        if (!isCompanyExist(company.getEmail(), company.getPassword())) {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, company.getName());
            values.put(2, company.getEmail());
            values.put(3, company.getPassword());
            try {
                return DbUtils.runQuery(DbManager.CREATE_COMPANY, values);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*
        The method that create a company and add it to the DB, it uses query from my DbUtil and send it
        an SQL statement I prepared beforehand in the DbManager.
        Boolean method so I can use the condition to present the user if the method worked or not.
     */

    @Override
    public boolean updateCompany(Company company) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, company.getName());
        values.put(2, company.getEmail());
        values.put(3, company.getId());
        try {
            return DbUtils.runQuery(DbManager.UPDATE_COMPANY, values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    /*
        Updating company in DB with SQL statement that was prepared beforehand in the DbManager
        and uses map to fill the blank so it can be used on all company instances.
     */

    @Override
    public void deleteCompany(int companyId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, companyId);
        try {
            DbUtils.runQuery(DbManager.DELETE_COMPANY, values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /*
        Delete a company with a given id that is sent into a query prepared beforehand with map
        to fill the blanks.
     */

    @Override
    public ArrayList<Company> getAllCompanies() {
        Connection connection = null;
        ResultSet resultSet;
        ArrayList<Company> companies = new ArrayList<>();
        try {
           connection = ConnectionPool.getInstance().getConnection();
           resultSet = connection.prepareStatement(DbManager.GET_ALL_COMPANIES).executeQuery();
           while (resultSet.next()){
               Company company = new Company(
                       resultSet.getInt("id"),
                       resultSet.getString("name"),
                       resultSet.getString("email"),
                       resultSet.getString("password")
               );
               companies.add(company);
           }
        } catch (SQLException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        return companies;
    }
    /*
        A method to get an arraylist of all the companies in the DB.
        The method get a connection fot the ConnectionPool and uses a ResultSet to get info from the DB,
        that with a while loop creating new instances of company with the info and add them to a arraylist
        that we return in the end.
     */

    @Override
    public Company getOneCompany(int companyId) {
        Company company = null;
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, companyId);
        ResultSet resultSet = DbUtils.runQueryWithResultSet(DbManager.GET_SINGLE_COMPANY,values);
        try {
            company = new Company(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return company;
    }
    /*
        Method that get the info of a specific company the user asked about with the use of ResultSet
     */
}
