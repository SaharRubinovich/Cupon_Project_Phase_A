package db;

import dbdao.CustomersDbDaoTest;
import junit.framework.JUnit4TestAdapter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

import static org.junit.Assert.*;

public class ConnectionPoolTest {
    private static ConnectionPool connectionPool;
    private static Stack<Connection> connections = new Stack<>();
    private static final int MAX_CONNECTIONS = 10;

    @BeforeClass
    public static void fillStack(){

    }
    @Test
    public void closeConnection() throws SQLException, InterruptedException {
        ConnectionPool.getInstance().closeConnection();
        assertEquals(0,connections.size());
    }

    @Test
    public void getInstance() throws SQLException, InterruptedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }

    @Test
    public void getConnection() throws SQLException, InterruptedException {
        assertEquals(10,connections.size());
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertEquals(9, connections.size());
    }

    @Test
    public void returnConnection() throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertEquals(9,connections.size());
        ConnectionPool.getInstance().returnConnection(connection);
        assertEquals(10,connections.size());
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CustomersDbDaoTest.class);
    }
}