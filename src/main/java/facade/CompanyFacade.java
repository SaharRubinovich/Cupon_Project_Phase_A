package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import db.DbCompanyManager;
import db.DbCouponManager;
import db.DbUtils;
import dbdao.CompaniesDbDao;
import dbdao.CouponsDbDao;
import exceptions.CompanyFacadeException;
import exceptions.LoginException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CompanyFacade extends ClientFacade {
    private int companyId;
    private boolean isOk;
    private static CouponsDbDao couponsDbDao = new CouponsDbDao();
    private static CompaniesDbDao companiesDbDao = new CompaniesDbDao();

    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    public CompanyFacade() {
        this(0);
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    @Override
    public boolean login(String email, String password) throws LoginException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        setOk(DbUtils.runQuery(DbCompanyManager.IS_COMPANY_EXIST, values));
        if (isOk) {
            System.out.println("Logged in successfully!");
            setCompanyId(companiesDbDao.getCompanyIdInLogin(email, password));
            return true;
        } else {
            throw new LoginException();
        }
    }

    public void addCoupon(Coupon coupon) throws CompanyFacadeException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, this.companyId);
        values.put(2, coupon.getTitle());
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCompanyManager.CHECK_IF_COUPON_EXIST, values);
            assert resultSet != null;
            resultSet.next();
            if (resultSet.getInt("counter") < 1) {
                couponsDbDao.addCoupons(coupon);
                System.out.println("Coupon was add");
                coupon.setId(couponsDbDao.getCouponId(values));
            } else {
                System.out.println("Coupon with the same name already exist for this company");
            }
        } catch (SQLException throwables) {
            throw new CompanyFacadeException("Error accord while trying to add new coupon");
        }
    }

    public void updateCoupon(Coupon coupon) throws CompanyFacadeException {
        if (couponsDbDao.updateCoupon(coupon)) {
            System.out.println("Coupon was updated!");
        } else {
            throw new CompanyFacadeException("Error accord while updating the coupon.");
        }
    }

    public void deleteCoupon(int couponId) throws CompanyFacadeException {
        Map<Integer, Object> id = new HashMap<>();
        id.put(1, couponId);
        if (DbUtils.runQuery(DbCouponManager.DELETE_COUPON, id) &&
                DbUtils.runQuery(DbCouponManager.DELETE_COUPON_PURCHASE_COMPANY, id)) {
            System.out.println("Coupon was deleted successfully!");
        } else {
            throw new CompanyFacadeException("Error accord while deleting coupon");
        }
    }

    public List<Coupon> getCompanyCoupons() {
        List<Coupon> coupons;
        List<Coupon> filteredList = new ArrayList<>();
        coupons = couponsDbDao.getAllCoupons();
        filteredList = coupons.stream().filter(coupon -> this.companyId == coupon.getCompanyId())
                .collect(Collectors.toList());
        return filteredList;
    }

    public List<Coupon> getCompanyCoupons(Category category) {
        List<Coupon> coupons;
        List<Coupon> filteredList;
        coupons = getCompanyCoupons();
        filteredList = coupons.stream().filter(categoryId -> category.value == categoryId.getCategory().value)
                .collect(Collectors.toList());
        return filteredList;
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        List<Coupon> coupons = getCompanyCoupons();
        return coupons.stream().filter(price -> price.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public Company getCompanyDetails() {
        return companiesDbDao.getOneCompany(getCompanyId());
    }
}
