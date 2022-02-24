package db;

import dbdao.CustomersDbDaoTest;
import junit.framework.JUnit4TestAdapter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

public class ConnectionPoolTest {
    private static ConnectionPool connectionPool;
    private static Stack<Connection> connections = new Stack<>();
    private static final int MAX_CONNECTIONS = 10;

    @Test
    public void closeConnection() throws SQLException, InterruptedException {
        ConnectionPool.getInstance().closeConnection();
        assertEquals(0, connections.size());
    }

    @Test
    public void getInstance() throws SQLException, InterruptedException {
        connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }

    @Test
    public void getConnection() throws SQLException, InterruptedException {
        connectionPool = ConnectionPool.getInstance();
        assertEquals(10, ConnectionPool.getCurrentStackSize());
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertEquals(9, ConnectionPool.getCurrentStackSize());
    }

    @Test
    public void returnConnection() throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertEquals(9, ConnectionPool.getCurrentStackSize());
        ConnectionPool.getInstance().returnConnection(connection);
        assertEquals(10, ConnectionPool.getCurrentStackSize());
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CustomersDbDaoTest.class);
    }
}