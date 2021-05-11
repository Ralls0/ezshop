package it.polito.ezshop.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // USER CLASS QUERIES

    public Integer getNextUserID() throws SQLException {
        String sql = "SELECT MAX(ID) as maxID FROM Users";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("maxID") + 1;
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
        String sql = "SELECT * FROM Users WHERE Username = '" + username + "'";
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
        String sql = "SELECT * FROM Users WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String username = res.getString("Username");
            String password = res.getString("Password");
            String role = res.getString("Role");

            return new EZUser(id, username, password, role);
        }
        return null;
    }

    public boolean searchUser(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Username = '" + username + "'";
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean searchUser(Integer id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean saveUser(User user) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Users (ID, Username, Password, Role) VALUES (?, ?, ?, ?)");
        statement.setInt(1, user.getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean updateUserRights(Integer id, String role) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Users SET Role = ? WHERE ID = ?");
        statement.setInt(2, id);
        statement.setString(1, role);

        statement.execute();
        statement.close();

        return true;
    }

    public boolean deleteUser(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Users WHERE ID = ?");
        statement.setInt(1, id);

        statement.execute();
        statement.close();

        return true;
    }

    // CUSTOMER CLASS QUERIES

    public Integer getNextCustomerID() throws SQLException {
        String sql = "SELECT MAX(ID) as maxID FROM Customers";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("maxID") + 1;
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
        String sql = "SELECT * FROM Customers WHERE ID = " + id;
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
        PreparedStatement statement = db.prepareStatement("INSERT INTO Customers (ID, Name, Card, Points) VALUES (?, ?, ?, ?)");
        statement.setInt(1, customer.getId());
        statement.setString(2, customer.getCustomerName());
        statement.setString(3, customer.getCustomerCard());
        statement.setInt(4, customer.getPoints());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean updateCustomer(Integer id, String newName, String newCard) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Customers SET Name = ?, Card = ? WHERE ID = ?");
        statement.setInt(3, id);
        statement.setString(1, newName);
        statement.setString(2, newCard);

        statement.execute();
        statement.close();

        return true;
    }

    public boolean deleteCustomer(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Customers WHERE ID = ?");
        statement.setInt(1, id);

        statement.execute();
        statement.close();

        return true;
    }

    public boolean searchCustomerByName(String customerName) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE Name = '" + customerName + "'";
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean searchCustomerById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean searchCustomerByCard(String card) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE ID = '" + card + "'";
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    // ORDER CLASS QUERIES

    public Integer getNextOrderID() throws SQLException {
        String sql = "SELECT MAX(ID) as maxID FROM Orders";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("maxID") + 1;
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

            EZOrder order = new EZOrder(orderID, balanceID, quantity, productCode, status, pricePerUnit);
            orders.add(order);
        }

        return orders;
    }

    public Order loadOrder(Integer id) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            Integer balanceID = res.getInt("BalanceID");
            String productCode = res.getString("ProductCode");
            String status = res.getString("Status");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            return new EZOrder(id, balanceID, quantity, productCode, status, pricePerUnit);
        }
        return null;
    }

    public boolean saveOrder(Order order) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Orders (ID, ProductCode, Status, PricePerUnit, Quantity, BalanceID) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, order.getOrderId());
        statement.setString(2, order.getProductCode());
        statement.setString(3, order.getStatus());
        statement.setDouble(4, order.getPricePerUnit());
        statement.setInt(5, order.getQuantity());
        statement.setInt(6, order.getBalanceId());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean updateOrder(Order toUpdate) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Orders SET Status = ?, BalanceID = ? WHERE ID = ?");
        statement.setString(1, toUpdate.getStatus());
        statement.setInt(2, toUpdate.getBalanceId());
        statement.setInt(3, toUpdate.getOrderId());

        statement.execute();
        statement.close();

        return true;
    }

    // PRODUCT CLASS QUERIES

    public Integer getNextProductID() throws SQLException {
        String sql = "SELECT MAX(ID) as maxID FROM Products";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("maxID") + 1;
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
            String location = res.getString("Position");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            EZProductType product = new EZProductType(id, quantity, productCode, description, note, location,
                    pricePerUnit);
            products.add(product);
        }

        return products;
    }

    public ProductType loadProduct(Integer id) throws SQLException {
        String sql = "SELECT * FROM Products WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            String productCode = res.getString("ProductCode");
            String description = res.getString("Description");
            String note = res.getString("Note");
            String location = res.getString("Position");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            return new EZProductType(id, quantity, productCode, description, note, location, pricePerUnit);
        }
        return null;
    }

    public ProductType loadProductByBarCode(String barCode) throws SQLException {
        String sql = "SELECT * FROM Products WHERE ProductCode = '" + barCode + "'";
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            Integer id = res.getInt("ID");
            String description = res.getString("Description");
            String note = res.getString("Note");
            String location = res.getString("Position");
            Integer quantity = res.getInt("Quantity");
            Double pricePerUnit = res.getDouble("PricePerUnit");

            return new EZProductType(id, quantity, barCode, description, note, location, pricePerUnit);
        }
        return null;
    }

    public boolean searchProductByBarCode(String barCode) throws SQLException {
        String sql = "SELECT * FROM Products WHERE ProductCode = '" + barCode + "'";
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean searchProductById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Products WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean searchProductByLocation(String location) throws SQLException {
        String sql = "SELECT * FROM Products WHERE Position = '" + location + "'";
        ResultSet res = db.executeSelectionQuery(sql);
        return res.next();
    }

    public boolean saveProduct(ProductType product) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO Products (ID, ProductCode, Description, Note, Position, Quantity, PricePerUnit) VALUES (?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, product.getId());
        statement.setString(2, product.getBarCode());
        statement.setString(3, product.getProductDescription());
        statement.setString(4, product.getNote());
        statement.setString(5, product.getLocation());
        statement.setInt(6, product.getQuantity());
        statement.setDouble(7, product.getPricePerUnit());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean updateProduct(ProductType product) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Products SET ProductCode = ?, Description = ?, Note = ?, Position = ?, Quantity = ?, PricePerUnit = ? WHERE ID = ?");
        statement.setInt(7, product.getId());
        statement.setString(1, product.getBarCode());
        statement.setString(2, product.getProductDescription());
        statement.setString(3, product.getNote());
        statement.setString(4, product.getLocation());
        statement.setInt(5, product.getQuantity());
        statement.setDouble(6, product.getPricePerUnit());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean deleteProduct(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Products WHERE ID = ?");
        statement.setInt(1, id);

        statement.execute();
        statement.close();

        return true;
    }

    // SALE CLASS QUERIES

    public Integer getNextSaleID() throws SQLException {
        String sql = "SELECT MAX(ID) as maxID FROM Sales";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("maxID") + 1;
            return nextID;
        }
        return 1;
    }

    public List<EZSaleTransaction> loadAllSales() throws SQLException {
        String sql = "SELECT * FROM Sales";
        ResultSet res = db.executeSelectionQuery(sql);
        List<EZSaleTransaction> sales = new ArrayList<>();
        while (res.next()) {
            Integer id = res.getInt("ID");
            String status = res.getString("Status");
            Double price = res.getDouble("Price");
            Double discountRate = res.getDouble("DiscountRate");

            EZSaleTransaction sale = new EZSaleTransaction(id);
            sale.setPrice(price);
            sale.setStatus(status);
            sale.setDiscountRate(discountRate);

            sql = "SELECT * FROM TicketsEntries WHERE SaleID = " + sale.getTicketNumber();
            ResultSet res2 = db.executeSelectionQuery(sql);
            while (res2.next()) {
                String productCode = res2.getString("ProductCode");
                String productDescription = res2.getString("ProductDescription");
                Double pricePerUnit = res2.getDouble("PricePerUnit");
                Integer quantity = res2.getInt("Quantity");
                Double productDiscountRate = res2.getDouble("DiscountRate");

                sale.addProductToSale(productCode, productDescription, pricePerUnit, productDiscountRate, quantity);
            }
            sales.add(sale);
        }

        return sales;
    }

    public EZSaleTransaction loadSale(Integer id) throws SQLException {
        String sql = "SELECT * FROM Sales WHERE ID = " + id;
        ResultSet res = db.executeSelectionQuery(sql);
        if (res.next()) {
            Double price = res.getDouble("Price");
            String status = res.getString("Status");
            Double discountRate = res.getDouble("DiscountRate");

            EZSaleTransaction sale = new EZSaleTransaction(id);
            sale.setPrice(price);
            sale.setStatus(status);
            sale.setDiscountRate(discountRate);

            sql = "SELECT * FROM TicketsEntries WHERE SaleID = " + sale.getTicketNumber();
            ResultSet res2 = db.executeSelectionQuery(sql);
            while (res2.next()) {
                String productCode = res2.getString("ProductCode");
                String productDescription = res2.getString("ProductDescription");
                Double pricePerUnit = res2.getDouble("PricePerUnit");
                Integer quantity = res2.getInt("Quantity");
                Double productDiscountRate = res2.getDouble("DiscountRate");

                sale.addProductToSale(productCode, productDescription, pricePerUnit, productDiscountRate, quantity);
            }

            return sale;
        }
        return null;
    }

    public boolean saveSale(EZSaleTransaction sale) throws SQLException {
        PreparedStatement statement = db
                .prepareStatement("INSERT INTO Sales (ID, Price, DiscountRate, Status) VALUES (?, ?, ?, ?)");
        statement.setInt(1, sale.getTicketNumber());
        statement.setDouble(2, sale.getPrice());
        statement.setDouble(3, sale.getDiscountRate());
        statement.setString(4, sale.getStatus());

        statement.execute();
        statement.close();

        statement = db.prepareStatement("INSERT INTO TicketsEntries (SaleID, ProductCode, ProductDescription, Quantity, DiscountRate, PricePerUnit) VALUES (?, ?, ?, ?, ?, ?)");
        for (TicketEntry entry : sale.getEntries()) {
            statement.setInt(1, sale.getTicketNumber());
            statement.setString(2, entry.getBarCode());
            statement.setString(3, entry.getProductDescription());
            statement.setInt(4, entry.getAmount());
            statement.setDouble(5, entry.getDiscountRate());
            statement.setDouble(6, entry.getPricePerUnit());

            statement.execute();
        }
        statement.close();

        return true;
    }

    public boolean updateSale(EZSaleTransaction sale) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE Sales SET Price = ?, DiscountRate = ?, Status = ? WHERE ID = ?");
        statement.setInt(4, sale.getTicketNumber());
        statement.setDouble(1, sale.getPrice());
        statement.setDouble(2, sale.getDiscountRate());
        statement.setString(3, sale.getStatus());

        statement.execute();
        statement.close();

        String sql = "SELECT * FROM TicketsEntries WHERE SaleID = " + sale.getTicketNumber();
        ResultSet res = db.executeSelectionQuery(sql);
        List<TicketEntry> dbEntries = new ArrayList<>();
        while (res.next()) {
            String productCode = res.getString("ProductCode");
            String productDescription = res.getString("ProductDescription");
            Double pricePerUnit = res.getDouble("PricePerUnit");
            Integer quantity = res.getInt("Quantity");
            Double productDiscountRate = res.getDouble("DiscountRate");

            dbEntries.add(new EZTicketEntry(productCode, productDescription, quantity, pricePerUnit, productDiscountRate));
        }

        List<TicketEntry> ticketEntriesToInsert = sale.getEntries().stream().filter( entry -> !dbEntries.stream().map(dbEntry -> dbEntry.getBarCode()).collect(Collectors.toList()).contains(entry.getBarCode())).collect(Collectors.toList());
        List<TicketEntry> ticketEntriesToRemove = dbEntries.stream().filter( dbEntry -> !sale.getEntries().stream().map(entry -> entry.getBarCode()).collect(Collectors.toList()).contains(dbEntry.getBarCode())).collect(Collectors.toList());
        List<TicketEntry> ticketEntriesToUpdate = sale.getEntries().stream().filter( entry -> dbEntries.stream().map(dbEntry -> dbEntry.getBarCode()).collect(Collectors.toList()).contains(entry.getBarCode())).collect(Collectors.toList());

        //  REMOVE NO LONGER PRESENT TRANSACTION ENTRIES FROM DB
        statement = db.prepareStatement("DELETE FROM TicketsEntries WHERE SaleID = ? AND ProductCode = ?");
        for (TicketEntry entry : ticketEntriesToRemove) {
            statement.setInt(1, sale.getTicketNumber());
            statement.setString(2, entry.getBarCode());

            statement.execute();
        }
        statement.close();

        //  INSERT NEW TRANSACTION ENTRIES IN DB
        statement = db.prepareStatement("INSERT INTO TicketsEntries (SaleID, ProductCode, ProductDescription, Quantity, DiscountRate, PricePerUnit) VALUES (?, ?, ?, ?, ?, ?)");
        for (TicketEntry entry : ticketEntriesToInsert) {
            statement.setInt(1, sale.getTicketNumber());
            statement.setString(2, entry.getBarCode());
            statement.setString(3, entry.getProductDescription());
            statement.setInt(4, entry.getAmount());
            statement.setDouble(5, entry.getDiscountRate());
            statement.setDouble(6, entry.getPricePerUnit());

            statement.execute();
        }
        statement.close();

        //  UPDATE TRANSACTION ENTRIES IN DB
        statement = db.prepareStatement("UPDATE TicketsEntries SET ProductDescription = ?, Quantity = ?, DiscountRate = ?, PricePerUnit = ? WHERE SaleID = ? AND ProductCode = ?");
        for (TicketEntry entry : ticketEntriesToUpdate) {
            statement.setString(1, entry.getProductDescription());
            statement.setInt(2, entry.getAmount());
            statement.setDouble(3, entry.getDiscountRate());
            statement.setDouble(4, entry.getPricePerUnit());
            statement.setInt(5, sale.getTicketNumber());
            statement.setString(6, entry.getBarCode());

            statement.execute();
        }
        statement.close();

        return true;
    }

    public boolean deleteSale(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM Sales WHERE ID = ?");
        statement.setInt(1, id);

        statement.execute();
        statement.close();

        return true;
    }

    // BALANCE CLASS QUERIES

    public Integer getNextBalanceOperationID() throws SQLException {
        String sql = "SELECT MAX(ID) as maxID FROM BalanceOperations";
        ResultSet res = db.executeSelectionQuery(sql);

        if (res.next()) {
            Integer nextID = res.getInt("maxID") + 1;
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
        String sql = "SELECT * FROM BalanceOperations WHERE ID = " + id;
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
        PreparedStatement statement = db.prepareStatement("INSERT INTO BalanceOperations (ID, Amount, Date, Type) VALUES (?, ?, ?, ?)");
        statement.setInt(1, operation.getBalanceId());
        statement.setDouble(2, operation.getMoney());
        statement.setString(3, operation.getDate().toString());
        statement.setString(4, operation.getType());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean updateBalanceOperation(BalanceOperation operation) throws SQLException {
        PreparedStatement statement = db.prepareStatement("UPDATE BalanceOperations SET Amount = ?, Date = ?, Type = ? WHERE ID = ?");
        statement.setInt(4, operation.getBalanceId());
        statement.setDouble(1, operation.getMoney());
        statement.setString(2, operation.getDate().toString());
        statement.setString(3, operation.getType());

        statement.execute();
        statement.close();

        return true;
    }

    public boolean deleteBalanceOperation(Integer id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("DELETE FROM BalanceOperations WHERE ID = ?");
        statement.setInt(1, id);

        statement.execute();
        statement.close();

        return true;
    }

    public DBConnector getConnector() {
        return db;
    }

    public void saveReturn(EZReturnTransaction openReturnTransaction) {
    }

    public EZReturnTransaction loadReturn(Integer returnId) {
        return null;
    }

    public Integer getNextReturnID() {
        return null;
    }

}
