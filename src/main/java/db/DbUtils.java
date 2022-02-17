package db;

import java.sql.*;
import java.util.Map;

public class DbUtils {
    private static Connection connection = null;

    public static void runQuery(String dbString) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(dbString);
            preparedStatement.execute();
            ConnectionPool.getInstance().returnConnection(connection);
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean runQuery(String dbString, Map<Integer, Object> values)  {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = fillStatement(dbString,values);
            preparedStatement.execute();
            return true;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static ResultSet runQueryWithResultSet(String sql, Map<Integer, Object> values) throws SQLException, InterruptedException {

            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = fillStatement(sql, values);
            ConnectionPool.getInstance().returnConnection(connection);
            return preparedStatement.executeQuery();

    }

    private static PreparedStatement fillStatement(String sql, Map<Integer, Object> values) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        values.forEach((key, value) -> {
            try {
                if (value instanceof Integer) {
                    preparedStatement.setInt(key, (Integer) value);
                } else if (value instanceof String) {
                    preparedStatement.setString(key, String.valueOf(value));
                } else if (value instanceof Double) {
                    preparedStatement.setDouble(key, (Double) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(key, (Date) value);
                }else if (value instanceof Boolean){
                    preparedStatement.setBoolean(key, (Boolean)value);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return preparedStatement;
    }
    public static ResultSet getAllInstancesWithResultSet(String sql) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ConnectionPool.getInstance().returnConnection(connection);
        return  preparedStatement.executeQuery(sql);
    }
}
