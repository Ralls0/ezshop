package it.polito.ezshop.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EZShopDBManager {

    private static EZShopDBManager instance;
    private DBConnector db;

    private EZShopDBManager() throws SQLException, ClassNotFoundException {
        this.db = DBConnector.getInstance();
    }

    public static EZShopDBManager getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new EZShopDBManager();
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

            EZUser user = new EZUser(id, username, password, role);
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
                return new EZUser(id, username, password, role);
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

            return new EZUser(id, username, password, role);
        }
        return null;
    }

    public boolean saveUser(User user) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Users (ID, Username, Password, Role) VALUES (?, '?', '?', '?')");
        statement.setInt(1, user.getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean updateUserRights(Integer id, String role) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Users SET Role = '?' WHERE ID = ?");
        statement.setInt(2, id);
        statement.setString(1, role);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean deleteUser(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Users WHERE ID = ?");
        statement.setInt(1, id);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
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

            EZCustomer customer = new EZCustomer(id, name, card, points);
            customers.add(customer);
        }

        return customers;
    }

    public Customer loadCustomer(Integer id) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String name = res.getString("Name");
            String card = res.getString("Card");
            Integer points = res.getInt("Points");

            return new EZCustomer(id, name, card, points);
        }
        return null;
    }

    public boolean saveCustomer(Customer customer) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Customers (ID, Name, Card, Points) VALUES (?, '?', '?', ?)");
        statement.setInt(1, customer.getId());
        statement.setString(2, customer.getCustomerName());
        statement.setString(3, customer.getCustomerCard());
        statement.setInt(4, customer.getPoints());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean updateCustomer(Integer id, String newName, String newCard) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Customers SET Name = '?', Card = '?' WHERE ID = ?");
        statement.setInt(3, id);
        statement.setString(1, newName);
        statement.setString(2, newCard);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean deleteCustomer(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Customers WHERE ID = ?");
        statement.setInt(1, id);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    //      ORDER CLASS QUERIES

    public Integer getNextOrderID() throws SQLException {
        String sql = "SELECT MAX(ID) FROM Orders";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("ID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<Order> loadAllOrders() throws SQLException {
        String sql = "SELECT * FROM Orders";
        ResultSet res = db.executeSelectionQuery(sql);
        List<Order> orders = new ArrayList<>();
        while (res.next()) {
            Integer orderID = res.getInt("ID");
            Integer balanceID = res.getInt("BalanceID");
            String productCode = res.getString("ProductCode");
            String status = res.getString("Status");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            EZOrder order = new EZOrder(orderID, balanceID, productCode, status, pricePerUnit, quantity);
            orders.add(order);
        }

        return orders;
    }

    public Order loadOrder(Integer id) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            Integer balanceID = res.getInt("BalanceID");
            String productCode = res.getString("ProductCode");
            String status = res.getString("Status");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            return new EZOrder(id, balanceID, productCode, status, pricePerUnit, quantity);
        }
        return null;
    }

    public boolean saveOrder(Order order) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Orders (ID, BalanceID, ProductCode, Status, PricePerUnit, Quantity) VALUES (?, '?', '?', ?, ?)");
        statement.setInt(1, order.getOrderId());
        statement.setString(2, order.getProductCode());
        statement.setString(3, order.getStatus());
        statement.setDouble(4, order.getPricePerUnit());
        statement.setInt(5, order.getQuantity());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean updateOrderStatus(Integer id, String status) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Orders SET Status = '?' WHERE ID = ?");
        statement.setInt(2, id);
        statement.setString(1, status);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    //      PRODUCT CLASS QUERIES

    public Integer getNextProductID() throws SQLException {
        String sql = "SELECT MAX(ID) FROM Products";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("ID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<ProductType> loadAllProducts() throws SQLException {
        String sql = "SELECT * FROM Products";
        ResultSet res = db.executeSelectionQuery(sql);
        List<ProductType> products = new ArrayList<>();
        while (res.next()) {
            Integer id = res.getInt("ID");
            String productCode = res.getString("ProductCode");
            String description = res.getString("Description");
            String note = res.getString("Note");
            String location = res.getString("Location");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            EZProductType product = new EZProductType(id, quantity, productCode, description, note, location, pricePerUnit);
            products.add(product);
        }

        return products;
    }

    public ProductType loadProduct(Integer id) throws SQLException {
        String sql = "SELECT * FROM Products WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String productCode = res.getString("ProductCode");
            String description = res.getString("Description");
            String note = res.getString("Note");
            String location = res.getString("Location");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            return new EZProductType(id, quantity, productCode, description, note, location, pricePerUnit);
        }
        return null;
    }

    public boolean saveProduct(ProductType product) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Products (ID, ProductCode, Description, Note, Location, Quantity, PricePerUnit) VALUES (?, '?', '?', '?', '?', ?, ?)");
        statement.setInt(1, product.getId());
        statement.setString(2, product.getBarCode());
        statement.setString(3, product.getProductDescription());
        statement.setString(4, product.getNote());
        statement.setString(4, product.getLocation());
        statement.setInt(4, product.getQuantity());
        statement.setDouble(4, product.getPricePerUnit());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean updateProduct(ProductType product) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Products SET ProductCode = '?', Description = '?', Note = '?', Location = '?', Quantity = ?, PricePerUnit = ? WHERE ID = ?");
        statement.setInt(7, product.getId());
        statement.setString(1, product.getBarCode());
        statement.setString(2, product.getProductDescription());
        statement.setString(3, product.getNote());
        statement.setString(4, product.getLocation());
        statement.setInt(5, product.getQuantity());
        statement.setDouble(6, product.getPricePerUnit());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean deleteProduct(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Products WHERE ID = ?");
        statement.setInt(1, id);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    //      SALE CLASS QUERIES

    public Integer getNextSaleID() throws SQLException {
        String sql = "SELECT MAX(ID) FROM Sales";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("ID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<SaleTransaction> loadAllSales() throws SQLException {
        String sql = "SELECT * FROM Sales";
        ResultSet res = db.executeSelectionQuery(sql);
        List<SaleTransaction> sales = new ArrayList<>();
        while (res.next()) {
            Integer id = res.getInt("ID");
            Double price = res.getDouble("Price");
            Double discountRate = res.getDouble("DiscountRate");

            EZSaleTransaction sale = new EZSaleTransaction(id);
            sale.setPrice(price);
            sale.setDiscountRate(discountRate);

            sql = "SELECT * FROM TicketsEntries WHERE SaleID = " + sale.getTicketNumber();
            ResultSet res2 = db.executeSelectionQuery(sql);
            while (res2.next()) {
                String productCode = res2.getString("ProductCode");
                String productDescription = res2.getString("ProductDescription");
                Double pricePerUnit = res2.getDouble("PricePerUnit");
                Integer quantity = res2.getInt("Quantity");
                Double productDiscountRate = res2.getDouble("DiscountRate");

                EZTicketEntry ticketEntry = new EZTicketEntry(productCode, productDescription, quantity, pricePerUnit, productDiscountRate);
                sale.addTicketEntry(ticketEntry);
            }
            sales.add(sale);
        }

        return sales;
    }

    public SaleTransaction loadSale(Integer id) throws SQLException {
        String sql = "SELECT * FROM Sales WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            Double price = res.getDouble("Price");
            Double discountRate = res.getDouble("DiscountRate");

            EZSaleTransaction sale = new EZSaleTransaction(id);
            sale.setPrice(price);
            sale.setDiscountRate(discountRate);

            sql = "SELECT * FROM TicketsEntries WHERE SaleID = " + sale.getTicketNumber();
            ResultSet res2 = db.executeSelectionQuery(sql);
            while (res2.next()) {
                String productCode = res2.getString("ProductCode");
                String productDescription = res2.getString("ProductDescription");
                Double pricePerUnit = res2.getDouble("PricePerUnit");
                Integer quantity = res2.getInt("Quantity");
                Double productDiscountRate = res2.getDouble("DiscountRate");

                EZTicketEntry ticketEntry = new EZTicketEntry(productCode, productDescription, quantity, pricePerUnit, productDiscountRate);
                sale.addTicketEntry(ticketEntry);
            }

            return sale;
        }
        return null;
    }

    public boolean saveSale(SaleTransaction sale) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Sales (ID, Price, DiscountRate) VALUES (?, ?, ?)");
        statement.setInt(1, sale.getTicketNumber());
        statement.setDouble(2, sale.getPrice());
        statement.setDouble(3, sale.getDiscountRate());

        boolean saleSaved = statement.execute();
        statement.close();

        statement = db.prepareStatement("INSERT INTO TicketsEntries (SaleID, ProductCode, ProductDescription, Quantity, DiscountRate, PricePerUnit) VALUES (?, '?', '?', ?, ?, ?)");
        for (TicketEntry entry : sale.getEntries()) {
            statement.setInt(1, sale.getTicketNumber());
            statement.setString(2, entry.getBarCode());
            statement.setString(3, entry.getProductDescription());
            statement.setInt(4, entry.getAmount());
            statement.setDouble(5, entry.getDiscountRate());
            statement.setDouble(6, entry.getPricePerUnit());

            statement.execute();
            //  TODO check if all ticket entry are inserted, and eventually revert all insertion
        }
        statement.close();

        return saleSaved;
    }

    public boolean deleteSale(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Sales WHERE ID = ?");
        statement.setInt(1, id);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    //      BALANCE CLASS QUERIES

    public Integer getNextBalanceOperationID() throws SQLException {
        String sql = "SELECT MAX(ID) FROM BalanceOperations";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("ID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<BalanceOperation> loadAllBalanceOperations() throws SQLException {
        String sql = "SELECT * FROM BalanceOperations";
        ResultSet res = db.executeSelectionQuery(sql);
        List<BalanceOperation> ops = new ArrayList<>();
        while (res.next()) {
            Integer id = res.getInt("ID");
            Double amount = res.getDouble("Amount");
            LocalDate date = res.getDate("Date").toLocalDate();
            String type = res.getString("Type");

            EZBalanceOperation operation = new EZBalanceOperation(id, date, amount, type);
            ops.add(operation);
        }

        return ops;
    }

    public BalanceOperation loadBalanceOperation(Integer id) throws SQLException {
        String sql = "SELECT * FROM BalanceOperations WHERE ID = "+ id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            Double amount = res.getDouble("Amount");
            LocalDate date = res.getDate("Date").toLocalDate();
            String type = res.getString("Type");

            return new EZBalanceOperation(id, date, amount, type);
        }
        return null;
    }

    public boolean saveBalanceOperation(BalanceOperation operation) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO BalanceOperations (ID, Amount, Date, Type) VALUES (?, ?, '?', '?')");
        statement.setInt(1, operation.getBalanceId());
        statement.setDouble(2, operation.getMoney());
        statement.setString(3, operation.getDate().toString());
        statement.setString(4, operation.getType());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean updateBalanceOperation(BalanceOperation operation) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE BalanceOperations SET Amount = ?, Date = '?', Type = '?' WHERE ID = ?");
        statement.setInt(4, operation.getBalanceId());
        statement.setDouble(1, operation.getMoney());
        statement.setString(2, operation.getDate().toString());
        statement.setString(3, operation.getType());

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public boolean deleteBalanceOperation(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM BalanceOperations WHERE ID = ?");
        statement.setInt(1, id);

        boolean returnValue = statement.execute();
        statement.close();

        return returnValue;
    }

    public DBConnector getConnector() {
        return db;
    }

}
