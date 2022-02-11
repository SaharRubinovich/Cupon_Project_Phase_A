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
    /*
        Strings that contain the code to create tables and schemes in MySQL
    */
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
    public static final String CREATE_COUPON = "INSERT INTO `coupon_project`.`coupons` " +
            "(company_id,category_id,title,description,start_date,end_date,amount,price,image) " +
            "VALUES(?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_COUPON = "UPDATE `coupon_project`.`coupons` " +
            "SET company_id=?, category_id=?, title=?, description=?, start_date=?, " +
            "end_date=?, amount=?, price=?, image=? WHERE id=?";
    public static final String DELETE_COUPON = "DELETE FROM `coupon_project`.`coupons` " +
            "WHERE id=?";
    public static final String GET_ALL_COUPONS = "SELECT * FROM `coupon_project`.`coupons` ";
    public static final String GET_SINGLE_COUPON = "SELECT * FROM `coupon_project`.`coupons` " +
            "WHERE id=?";

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

