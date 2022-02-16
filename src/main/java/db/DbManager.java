package db;

import java.sql.SQLException;

public class DbManager {
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
    public static final String CREATE_COMPANIES_TABLE = "CREATE TABLE IF NOT EXISTS `coupon_project`.`companies`" +
            " (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
    public static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE IF NOT EXISTS `coupon_project`.`customers`" +
            " (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `first_name` VARCHAR(45) NOT NULL," +
            "  `last_name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
    public static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS `coupon_project`.`categories` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";
    public static final String CREATE_COUPONS_TABLE =
            "CREATE TABLE IF NOT EXISTS `coupon_project`.`coupons` (" +
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
                    "  PRIMARY KEY (`id`)," +
                    "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE," +
                    "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE," +
                    "  CONSTRAINT `company_id`" +
                    "    FOREIGN KEY (`company_id`)" +
                    "    REFERENCES `coupon_project`.`companies` (`id`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `category_id`" +
                    "    FOREIGN KEY (`category_id`)" +
                    "    REFERENCES `coupon_project`.`categories` (`id`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);";
    public static final String CREATE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXISTS" +
            " `coupon_project`.`customers_vs_coupons` (" +
            "  `customer_id` INT NOT NULL," +
            "  `coupon_id` INT NOT NULL," +
            "  PRIMARY KEY (`customer_id`, `coupon_id`)," +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "  CONSTRAINT `customr_id`" +
            "    FOREIGN KEY (`customer_id`)" +
            "    REFERENCES `coupon_project`.`customers` (`id`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION," +
            "  CONSTRAINT `coupon_id`" +
            "    FOREIGN KEY (`coupon_id`)" +
            "    REFERENCES `coupon_project`.`coupons` (`id`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION);";

    public static final String DROP_COMPANIES_TABLE = "DROP TABLE IF EXISTS `coupon_project`.`companies`";
    public static final String DROP_CUSTOMERS_TABLE = "DROP TABLE IF EXISTS `coupon_project`.`customers`";
    public static final String DROP_CATEGORIES_TABLE = "DROP TABLE IF EXISTS `coupon_project`.`categories`";
    public static final String DROP_COUPONS_TABLE = "DROP TABLE IF EXISTS `coupon_project`.`coupons`";
    public static final String DROP_CUSTOMER_VS_COUPONS_TABLE = "DROP TABLE IF EXISTS `coupon_project`.`customers_vs_coupons`";
    public static final String ADD_COUPON_PURCHASE = "INSERT INTO `coupon_project`.`customers_vs_coupons` " +
            "(`customer_id`,`coupon_id`) VALUES(?,?)";
    /*
            Strings that contain the code to create tables and schemes in MySQL
        */

    public static void createDb() throws SQLException, InterruptedException {
        DbUtils.runQuery(CREATE_DB);
    }

    public static void dropDb() throws SQLException, InterruptedException {
        DbUtils.runQuery(DROP_DB);
    }

    public static void createCompaniesTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(CREATE_COMPANIES_TABLE);
    }

    public static void createCustomersTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(CREATE_CUSTOMERS_TABLE);
    }

    public static void createCategoriesTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(CREATE_CATEGORIES_TABLE);
    }

    public static void createCouponsTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(CREATE_COUPONS_TABLE);
    }

    public static void createCustomersVsCouponsTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(CREATE_CUSTOMERS_VS_COUPONS);
    }

    public static void dropCompaniesTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(DROP_COMPANIES_TABLE);
    }

    public static void dropCustomersTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(DROP_CUSTOMERS_TABLE);
    }

    public static void dropCategoriesTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(DROP_CATEGORIES_TABLE);
    }

    public static void dropCouponsTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(DROP_COUPONS_TABLE);
    }

    public static void dropCustomersVsCouponsTable() throws SQLException, InterruptedException {
        DbUtils.runQuery(DROP_CUSTOMER_VS_COUPONS_TABLE);
    }
}

