package dbdao;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import dao.CouponsDao;
import db.ConnectionPool;
import db.DbManager;
import db.DbUtils;
import exceptions.GetCouponException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponsDbDao implements CouponsDao {
    private ConnectionPool connectionPool;

    //methods
    @Override
    public boolean addCoupons(Coupon coupon) {
        Map<Integer, Object> values = createMapForSqlQuery(coupon);
        return DbUtils.runQuery(DbManager.CREATE_COUPON, values);
    }

    @Override
    public boolean updateCoupon(Coupon coupon) {
        Map<Integer, Object> values = createMapForSqlQuery(coupon);
        values.put(values.size() + 1, coupon.getId());
        return DbUtils.runQuery(DbManager.UPDATE_COUPON, values);
    }

    @Override
    public void deleteCoupon(int couponId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, couponId);
        DbUtils.runQuery(DbManager.DELETE_COUPON, values);
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            ResultSet resultSet = DbUtils.getAllInstancesWithResultSet(DbManager.GET_ALL_COUPONS);
            while (resultSet.next()) {
                Coupon coupon = createCouponInstance(resultSet);
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException e) {
            throw new GetCouponException();
        }
        return coupons;
    }

    @Override
    public Coupon getOneCoupon(int couponId) {
        Coupon coupon = null;
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, couponId);
        try {
            ResultSet resultSet = DbUtils.runQueryWithResultSet(DbManager.GET_SINGLE_COUPON, values);
            resultSet.next();
            coupon = createCouponInstance(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new GetCouponException();
        }
        return coupon;
    }

    @Override
    public boolean addCouponPurchase(int customerId, int couponId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1,customerId);
        values.put(2,couponId);
        return DbUtils.runQuery(DbManager.ADD_COUPON_PURCHASE,values);
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customerId);
        values.put(2, couponId);
        DbUtils.runQuery(DbManager.DELETE_COUPON_PURCHASE,values);
    }

    private Map<Integer, Object> createMapForSqlQuery(Coupon coupon) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, coupon.getCompanyId());
        values.put(2, coupon.getCategory());
        values.put(3, coupon.getTitle());
        values.put(4, coupon.getDescription());
        values.put(5, coupon.getStartDate());
        values.put(6, coupon.getEndDate());
        values.put(7, coupon.getAmount());
        values.put(8, coupon.getPrice());
        values.put(9, coupon.getImage());
        return values;
    }

    private Coupon createCouponInstance(ResultSet resultSet) throws SQLException {
        return new Coupon(
                resultSet.getInt("id"),
                resultSet.getInt("company_id"),
                (Category) resultSet.getObject("category_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getDate("start_date"),
                resultSet.getDate("end_date"),
                resultSet.getInt("amount"),
                resultSet.getDouble("price"),
                resultSet.getString("image")
        );
    }
}
