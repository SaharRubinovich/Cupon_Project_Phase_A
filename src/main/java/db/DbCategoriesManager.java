package db;

public class DbCategoriesManager {
    public static final String ADD_CATEGORY = "INSERT INTO `coupon_project`.`categories` " +
            "(id, name) VALUES (?,?);";
    public static final String UPDATE_CATEGORY = " UPDATE `coupon_project`.`categories` SET name=? WHERE id=? ";
    public static final String DELETE_CATEGORY = " DELETE FROM `coupon_project`.`categories` WHERE id=? ";
    public static final String GET_ALL_CATEGORIES = " SELECT * FROM `coupon_project`.`categories`";
    public static final String GET_CATEGORY_BY_ID = " SELECT * FROM  `coupon_project`.`categories` WHERE id=? ";
}
