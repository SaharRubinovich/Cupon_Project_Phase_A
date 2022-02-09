package db;

public class dbManager {
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String SQL_USER = "root";
    public static final String SQL_PASS = "12345678";
    /*
    * The info for accessing the db to give to the connection pool so the connection will work*/
    public static final String CREATE_DB = "CREATE SCHEMA `coupon_project`";
    public static final String DROP_DB = "DROP SCHEMA `coupon_project`";
    /*
    Two string that are responsible for creating and deleting all our work environment.
     */
    //Tables creation
    public static final String CREATE_COMPANIES_TABLE = "CREATE TABLE IF NOT EXIST `coupon_project`.`companies`" +
            " (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
    public static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE IF NOT EXIST `coupon_project`.`customers`" +
            " (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `first_name` VARCHAR(45) NOT NULL," +
            "  `last_name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
    public static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXIST `coupon_project`.`categories` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
    public static final String CREATE_COUPONS_TABLE = "CREATE TABLE `coupon_project`.`coupons` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `company_id` INT NOT NULL," +
            "  `category_id` INT NOT NULL," +
            "  `title` VARCHAR(45) NOT NULL," +
            "  `description` VARCHAR(45) NOT NULL," +
            "  `start_date` DATE NOT NULL," +
            "  `end_date` DATE NOT NULL," +
            "  `amount` INT NOT NULL," +
            "  `price` DOUBLE NOT NULL," +
            "  `image` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
        public static final String CREATE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXIST " +
                "`coupon_project`.`customers_vs_coupons` (" +
                "  `customer_id` INT NOT NULL," +
                "  `coupon_id` INT NOT NULL," +
                "  PRIMARY KEY (`customer_id`, `coupon_id`));";
        //todo: add create string for customer_vs_coupon and add the foreign key.


}
