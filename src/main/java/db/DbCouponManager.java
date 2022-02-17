package db;

public class DbCouponManager {
    public static final String CREATE_COUPON = "INSERT INTO `coupon_project`.`coupons` " +
            "(company_id,category_id,title,description,start_date,end_date,amount,price,image) " +
            "VALUES(?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_COUPON = "UPDATE `coupon_project`.`coupons` " +
            "SET company_id=?, category_id=?, title=?, description=?, start_date=?, " +
            "end_date=?, amount=?, price=?, image=? WHERE id=?";
    public static final String UPDATE_COUPON_AMOUNT = "UPDATE `coupon_project`.`coupons` " +
            "WHERE id=? SET amount=?";
    public static final String DELETE_COUPON = "DELETE FROM `coupon_project`.`coupons` " +
            "WHERE id=?";
    public static final String GET_ALL_COUPONS = "SELECT * FROM `coupon_project`.`coupons` ";
    public static final String GET_SINGLE_COUPON = "SELECT * FROM `coupon_project`.`coupons` " +
            "WHERE id=?";
    public static final String DELETE_COUPON_PURCHASE_BY_CUSTOMER = "DELETE FROM `coupon_project`.`customers_vs_coupons` " +
            "WHERE customer_id=?, coupon_id=?";
    public static final String GET_CUSTOMER_COUPONS_BY_ID = "SELECT FROM `coupon_project`.`customers_vs_coupons` " +
            "WHERE customer_id=?";
    public static final String DELETE_COUPON_PURCHASE_COMPANY ="DELETE FROM `coupon_project`.`customers_vs_coupons` " +
            "WHERE coupon_id=?";
    public static final String EXPIRE_COUPONS_DELETE = "SET SQL_SAFE_UPDATES=0;" +
            "DELETE FROM `coupon_project`.`coupons` WHERE `end_date` < curdate();" +
            "SET SQL_SAFE_UPDATES=1;";
}
