package dao;

import beans.Company;

import java.util.List;

public interface CompaniesDao {
    boolean isCompanyExist(String email, String password);
    boolean addCompany(Company company);
    boolean updateCompany(Company company);
    void deleteCompany(int companyId);
    List<Company> getAllCompanies();
    Company getOneCompany(int companyId);
}
