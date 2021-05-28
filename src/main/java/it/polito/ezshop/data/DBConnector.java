package it.polito.ezshop.data;

import java.sql.*;

public class DBConnector {
    private final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String JDBC_URL = "jdbc:derby:ezshop;create=true";

    private static DBConnector instance;
    private Connection connection;

    private DBConnector() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(JDBC_URL);
    }

    public static DBConnector getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public void execute(String sql) throws SQLException {
        connection.createStatement().execute(sql);
    }

    public ResultSet executeSelectionQuery(String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
