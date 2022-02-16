package db;

public class DbCompanyManager {
    public static final String CREATE_COMPANY = "INSERT INTO `coupon_project`.`companies` " +
            "(name,email,password) VALUES(?,?,?)";
    public static final String IS_COMPANY_EXIST = "SELECT count(*) FROM `coupon_project`.`companies` " +
            "WHERE email=? AND password=?";
    public static final String UPDATE_COMPANY = "UPDATE `coupon_project`.`companies` " +
            "SET name=?, email=? WHERE id=?";
    public static final String DELETE_COMPANY = "DELETE FROM `coupon_project`.`companies` " +
            "WHERE id=?";
    public static final String GET_ALL_COMPANIES = "SELECT * FROM `coupon_project`.`companies` ";
    public static final String GET_SINGLE_COMPANY = "SELECT * FROM `coupon_project`.`companies` " +
            "WHERE id=?";
    public static final String CHECK_IF_COUPON_EXIST = "SELECT COUNT(*) FROM `coupon_project`.`coupons` " +
            "WHERE company_id=?, title=?";
}
