package db;

public class DbCustomerManager {
    public static final String CREATE_CUSTOMER = "INSERT INTO `coupon_project`.`customers` " +
            "(first_name,last_name,email,password) " +
            "VALUES(?,?,?,?)";
    public static final String IS_CUSTOMER_EXIST = "SELECT count(*) FROM `coupon_project`.`customers` " +
            "WHERE email=? AND password=?";
    public static final String UPDATE_CUSTOMER = "UPDATE `coupon_project`.`customers` " +
            "SET first_name=?, last_name=?, email=? WHERE id=?";
    public static final String DELETE_CUSTOMER = "DELETE FROM `coupon_project`.`customers` " +
            "WHERE id=?";
    public static final String GET_ALL_CUSTOMERS = "SELECT * FROM `coupon_project`.`customers` ";
    public static final String GET_SINGLE_CUSTOMER = "SELECT * FROM `coupon_project`.`customers` " +
            "WHERE id=?";
    public static final String CHECK_IF_BOUGHT_COUPON = "SELECT COUNT(*) FROM `coupon_project`.`customers_vs_coupons " +
            "WHERE customer_id=?, coupon_id=?";
}
