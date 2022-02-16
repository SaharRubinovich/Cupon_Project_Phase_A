package facade;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import db.DbCouponManager;
import db.DbCustomerManager;
import db.DbUtils;
import dbdao.CouponsDbDao;
import dbdao.CustomersDbDao;
import exceptions.CheckingPurchaseException;
import exceptions.CustomerFacadeException;
import exceptions.LoginException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerFacade extends ClientFacade {
    private static CustomersDbDao customersDbDao = new CustomersDbDao();
    private static CouponsDbDao couponsDbDao = new CouponsDbDao();
    private int customerId;

    public CustomerFacade(int customerId) {
        setCustomerId(customerId);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int companyId) {
        this.customerId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws LoginException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, email);
        values.put(2, password);
        boolean allOk = DbUtils.runQuery(DbCustomerManager.IS_CUSTOMER_EXIST, values);
        if (allOk){
            System.out.println("Logged in successfully!");
            return true;
        } else {
            throw new LoginException();
        }
    }

    public void purchaseCoupon(Coupon coupon) throws CustomerFacadeException {
        if (customersDbDao.checkPurchase(this.customerId, coupon.getId())) {
            throw new CustomerFacadeException("Cannot buy more than 1 of the same coupon");
        }
        if (coupon.getAmount() < 1) {
            throw new CustomerFacadeException("Coupon #" + coupon.getId() + " Is out of stock");
        }
        if (coupon.getEndDate().before(new Date(System.currentTimeMillis()))) {
            throw new CustomerFacadeException("The coupon is already expired");
        }
        couponsDbDao.addCouponPurchase(this.customerId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, coupon.getId());
        values.put(2, coupon.getAmount());
        DbUtils.runQuery(DbCouponManager.UPDATE_COUPON_AMOUNT, values);
        System.out.println("Coupon was purchased");
    }

    public List<Coupon> getCustomerCoupons() throws CustomerFacadeException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> id = new HashMap<>();
        id.put(1, this.customerId);
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCouponManager.GET_CUSTOMER_COUPONS_BY_ID, id);
            while (resultSet.next()) {
                coupons.add(couponsDbDao.getOneCoupon(resultSet.getInt("coupon_id")));
            }
        } catch (SQLException | InterruptedException e) {
            throw new CustomerFacadeException("There was an error while trying to get customer coupons");
        }
        return coupons;
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        ArrayList<Coupon> customerCoupons = null;
        try {
            customerCoupons = (ArrayList<Coupon>) getCustomerCoupons();
        } catch (CustomerFacadeException e) {
            System.out.println(e.getMessage());
        }
        Predicate<Integer> categoryId = category.value;
        return customerCoupons.stream()
                .filter(couponCategory -> categoryId.equals(couponCategory.getCategory().value))
                .collect(Collectors.toList());
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) {
        ArrayList<Coupon> coupons = null;
        try {
            coupons = (ArrayList<Coupon>) getCustomerCoupons();
        } catch (CustomerFacadeException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<Coupon> filteredList = (ArrayList<Coupon>) coupons.stream()
                .filter(coupon -> maxPrice>=coupon.getPrice())
                .collect(Collectors.toList());
        return filteredList;
    }
    public Customer getCustomerDetails(){
        return customersDbDao.getOneCustomer(this.customerId);
    }
}
