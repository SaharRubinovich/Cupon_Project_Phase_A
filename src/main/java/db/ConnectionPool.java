package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    private static final int MAX_CONNECTIONS = 10;
    private static ConnectionPool instance = null;
    private static final Stack<Connection>connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        openConnection();
    }

    private void openConnection() throws SQLException {
        for (int index = 0; index < MAX_CONNECTIONS; index++) {
            Connection connection = DriverManager.getConnection(DbManager.URL, DbManager.SQL_USER, DbManager.SQL_PASS);
            connections.push(connection);
        }
    }
    /*Ctor fot Singleton class to create connections for db and filling the connection stack */
    public void closeConnection() throws InterruptedException {
        synchronized (connections){
            while (connections.size()<MAX_CONNECTIONS){
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
    /*
    Method to close the open connection so we won't have connection to the db when we finish our work.
    will not close itself if there is still a connection in progress to users won't suddenly disconnect
     */
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null){
            synchronized (ConnectionPool.class){
                if (instance == null){
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }
    /*
    The method to create connection, the only way to create connection pool is here.
    we do double check in case someway to instances bypass the first if and sync so we won't have errors.
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections){
            if (connections.empty()){
                connections.wait();
            }
            return connections.pop();
        }
    }
    /*
    The methods that give connection to whom try to get it. make them wait if there is no connection
    available at the moment.
     */
    public void returnConnection(Connection connection){
        synchronized (connections){
            connections.push(connection);
            connections.notify();
        }
    }
    /*
    The method that return a connection when the user done with it.
    notify the rest of the connections that now another connection is free so if there is a connection
    request waiting it can stop wait and start to work.
     */
    public static int getCurrentStackSize(){
        return connections.size();
    }
    /*
        Static method to check the current amount of available connections.
        used in the testing to check the other connection pool methods.
     */
}
