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

    public CustomerFacade() {
        this(0);
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
            setCustomerId(customersDbDao.getCustomerIdInLogin(email, password));
            return true;
        } else {
            throw new LoginException();
        }
    }
    /*
        Login method, will check the db with the info we provided to see if there is a customer that matches it.
        if there is will return true, else will throw custom exception.
     */

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
    /*
        Method to purchase a coupon for the customer. will check if the customer already purchase the coupon he want,
        check if there is any coupon at all to buy and will check if the coupon manged to escape the daily job and stayed
        even through the expiry date already passed. if all will be ok that purchase will happen and the customer vs
        coupon table will be updated.
     */

    public List<Coupon> getCustomerCoupons() throws CustomerFacadeException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> id = new HashMap<>();
        id.put(1, this.customerId);
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbCouponManager.GET_CUSTOMER_COUPONS_BY_ID, id);
            assert resultSet != null;
            while (resultSet.next()) {
                coupons.add(couponsDbDao.getOneCoupon(resultSet.getInt("coupon_id")));
            }
        } catch (SQLException e) {
            throw new CustomerFacadeException("There was an error while trying to get customer coupons");
        }
        return coupons;
    }
    /*
        return a list of the customer that the customer bought through the info in the customer vs coupon table in the
        db.
     */

    public List<Coupon> getCustomerCoupons(Category category) {
        ArrayList<Coupon> customerCoupons = null;
        try {
            customerCoupons = (ArrayList<Coupon>) getCustomerCoupons();
        } catch (CustomerFacadeException e) {
            System.out.println(e.getMessage());
        }
        int categoryId = category.value;
        return customerCoupons.stream()
                .filter(couponCategory -> categoryId == couponCategory.getCategory().value)
                .collect(Collectors.toList());
    }
    /*
        give a list of the customer purchase coupons but filter it with the category it was asked for.
     */

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
    /*
        Same as above, now filter with max price in mind
     */
    public Customer getCustomerDetails(){
        return customersDbDao.getOneCustomer(this.customerId);
    }
    /*
        give the customer info of the customer we logged into at the start.
     */
}
