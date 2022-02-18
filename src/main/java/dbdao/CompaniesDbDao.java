package dbdao;

import beans.Company;
import beans.Coupon;
import dao.CompaniesDao;
import db.ConnectionPool;
import db.DbCompanyManager;
import db.DbUtils;
import exceptions.CompanyAlreadyExistException;
import exceptions.GetCompanyException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDbDao implements CompaniesDao {
    private ConnectionPool connectionPool;

    //methods
    @Override
    public boolean isCompanyExist(String email, String password) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        ResultSet resultSet = null;
        resultSet = DbUtils.runQueryWithResultSet(DbCompanyManager.IS_COMPANY_EXIST, values);
        try {
            assert resultSet != null;
            resultSet.next();
            if (resultSet.getInt("counter") > 0){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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
            return DbUtils.runQuery(DbCompanyManager.CREATE_COMPANY, values);
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
        return DbUtils.runQuery(DbCompanyManager.UPDATE_COMPANY, values);
    }
    /*
        Updating company in DB with SQL statement that was prepared beforehand in the DbManager
        and uses map to fill the blank so it can be used on all company instances.
     */

    @Override
    public void deleteCompany(int companyId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, companyId);
        DbUtils.runQuery(DbCompanyManager.DELETE_COMPANY, values);
    }
    /*
        Delete a company with a given id that is sent into a query prepared beforehand with map
        to fill the blanks.
     */

    @Override
    public List<Company> getAllCompanies() {
        Connection connection = null;
        ResultSet resultSet;
        ArrayList<Company> companies = new ArrayList<>();
        try {
           connection = ConnectionPool.getInstance().getConnection();
           resultSet = connection.prepareStatement(DbCompanyManager.GET_ALL_COMPANIES).executeQuery();
           while (resultSet.next()){
               companies.add(buildCompanyInstance(resultSet));
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
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCompanyManager.GET_SINGLE_COMPANY,values);
            assert resultSet != null;
            resultSet.next();
            company = buildCompanyInstance(resultSet);
        } catch (SQLException throwables) {
            throw new GetCompanyException();
        }
        return company;
    }
    /*
        Method that get the info of a specific company the user asked about with the use of ResultSet
     */
    private Company buildCompanyInstance(ResultSet resultSet) throws SQLException {
        return new Company(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
    /*
        Private method to build a company instance instead of using double code in 2 different methods
     */
    public int getCompanyIdInLogin(String email, String password){
        Map<Integer, Object> values = new HashMap<>();
        values.put(1,email);
        values.put(2,password);
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCompanyManager.GET_SINGLE_ID,values);
            assert resultSet != null;
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new GetCompanyException();
        }
    }
}
