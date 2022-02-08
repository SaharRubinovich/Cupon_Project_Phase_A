package db;

public class dbManager {
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String SQL_USER = "root";
    public static final String SQL_PASS = "12345678";
    /*
    * The info for accessing the db to give to the connection pool so the connection will work*/
    public static final String CREATE_DB = "CREATE SCHEMA `cupon_project`";
    public static final String DROP_DB = "DROP SCHEMA `cupon_project`";
    /*
    Two string that are responsible for creating and deleting all our work environment.
     */

}
