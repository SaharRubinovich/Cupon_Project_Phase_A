package dbdao;

import beans.Coupon;
import beans.Customer;
import db.ConnectionPool;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CouponsDbDaoTest {
    CouponsDbDao couponsDbDao = new CouponsDbDao();
    CustomersDbDao customersDbDao = new CustomersDbDao();
    Connection connection = ConnectionPool.getInstance().getConnection();

    private Coupon coupon = new Coupon(0, 3, 3, "TestingJunit", "test",
            Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(12)), 5, 33, "none");

    public CouponsDbDaoTest() throws SQLException, InterruptedException {
    }

    @Test
    public void addCoupons() {
        assertTrue(couponsDbDao.addCoupons(coupon));
    }

    @Test
    public void updateCoupon() throws SQLException {
        coupon.setDescription("New description");
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, coupon.getCompanyId());
        values.put(2, coupon.getTitle());
        coupon.setId(couponsDbDao.getCouponId(values));
        couponsDbDao.updateCoupon(coupon);
        Coupon test = couponsDbDao.getOneCoupon(coupon.getId());
        assertEquals("New description", test.getDescription());
    }

    @Test
    public void deleteCoupon() throws SQLException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, coupon.getCompanyId());
        values.put(2, coupon.getTitle());
        coupon.setId(couponsDbDao.getCouponId(values));
        couponsDbDao.deleteCoupon(coupon.getId());
    }

    @Test
    public void getAllCoupons() {
        List<Coupon> couponList = couponsDbDao.getAllCoupons();
        assertEquals(2,couponList.size());
    }

    @Test
    public void getOneCoupon() throws SQLException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, coupon.getCompanyId());
        values.put(2, coupon.getTitle());
        coupon.setId(couponsDbDao.getCouponId(values));
        coupon.setDescription("New description");
        Coupon test = couponsDbDao.getOneCoupon(coupon.getId());
        assertEquals(coupon,test);
    }

    @Test
    public void addCouponPurchase() {
        Customer customer = customersDbDao.getOneCustomer(1);
        assertTrue(couponsDbDao.addCouponPurchase(1,3));
    }

    @Test
    public void deleteCouponPurchase() {
        Customer customer = customersDbDao.getOneCustomer(1);
        couponsDbDao.deleteCouponPurchase(1,3);
        assertFalse(customersDbDao.checkPurchase(1,3));
    }

    @Test
    public void getCouponId() throws SQLException {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, coupon.getCompanyId());
        values.put(2, coupon.getTitle());
        assertEquals(3,couponsDbDao.getCouponId(values));
    }
    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(CouponsDbDaoTest.class);
    }
}