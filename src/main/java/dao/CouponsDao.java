package dao;

import beans.Coupon;

import java.util.List;

public interface CouponsDao {
    boolean addCoupons(Coupon coupon);
    boolean updateCoupon(Coupon coupon);
    void deleteCoupon(int couponId);
    List<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponId);
    boolean addCouponPurchase(int customerId, int couponId);
    void deleteCouponPurchase(int customerId, int couponId);
}
