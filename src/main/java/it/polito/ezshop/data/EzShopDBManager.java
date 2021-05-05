package it.polito.ezshop.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EzShopDBManager {

    private static EzShopDBManager instance;
    private DBConnector db;

    private EzShopDBManager() throws SQLException, ClassNotFoundException {
        this.db = DBConnector.getInstance();
    }

    public static EzShopDBManager getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new EzShopDBManager();
        }
        return instance;
    }

    //      USER CLASS QUERIES

    public Integer getNextUserID() throws SQLException {
        String sql = "SELECT MAX(ID) FROM Users";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("ID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<User> loadAllUsers() throws SQLException {
        String sql = "SELECT * FROM Users";
        ResultSet res = db.executeSelectionQuery(sql);
        List<User> users = new ArrayList<>();
        while (res.next()) {
            Integer id = res.getInt("ID");
            String username = res.getString("Username");
            String password = res.getString("Password");
            String role = res.getString("Role");

            EzUser user = new EzUser(id, username, password, role);
            users.add(user);
        }

        return users;
    }

    public User loadUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Username = '"+ username +"'";
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String pass = res.getString("Password");
            if (pass.equals(password)) {
                Integer id = res.getInt("ID");
                String role = res.getString("Role");
                return new EzUser(id, username, password, role);
            }
        }
        return null;
    }

    public User loadUser(Integer id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String username = res.getString("Username");
            String password = res.getString("Password");
            String role = res.getString("Role");

            return new EzUser(id, username, password, role);
        }
        return null;
    }

    public boolean saveUser(User user) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Users (ID, Username, Password, Role) VALUES (?, '?', '?', '?')");
        statement.setInt(1, user.getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole());

        return statement.execute();
    }

    public boolean updateUserRights(Integer id, String role) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Users SET Role = '?' WHERE ID = ?");
        statement.setInt(2, id);
        statement.setString(1, role);

        return statement.execute();
    }

    public boolean deleteUser(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Users WHERE ID = ?");
        statement.setInt(1, id);

        return statement.execute();
    }

    //      CUSTOMER CLASS QUERIES

    public Integer getNextCustomerID() throws SQLException {
        String sql = "SELECT MAX(ID) FROM Customers";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("ID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<Customer> loadAllCustomers() throws SQLException {
        String sql = "SELECT * FROM Customers";
        ResultSet res = db.executeSelectionQuery(sql);
        List<Customer> customers = new ArrayList<>();
        while (res.next()) {
            Integer id = res.getInt("ID");
            String name = res.getString("Name");
            String card = res.getString("Card");
            Integer points = res.getInt("Points");

            EzCustomer customer = new EzCustomer(id, name, card, points);
            customers.add(customer);
        }

        return customers;
    }

    public Customer loadCustomer(Integer id) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String name = res.getString("Name");
            String card = res.getString("Card");
            Integer points = res.getInt("Points");

            return new EzCustomer(id, name, card, points);
        }
        return null;
    }

    public boolean saveCustomer(Customer customer) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Customers (ID, Name, Card, Points) VALUES (?, '?', '?', ?)");
        statement.setInt(1, customer.getId());
        statement.setString(2, customer.getCustomerName());
        statement.setString(3, customer.getCustomerCard());
        statement.setInt(4, customer.getPoints());

        return statement.execute();
    }

    public boolean updateCustomer(Integer id, String newName, String newCard) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Customers SET Name = '?', Card = '?' WHERE ID = ?");
        statement.setInt(4, id);
        statement.setString(1, newName);
        statement.setString(2, newCard);

        return statement.execute();
    }

    public boolean deleteCustomer(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Customers WHERE ID = ?");
        statement.setInt(1, id);

        return statement.execute();
    }

    public DBConnector getConnector() {
        return db;
    }

}
