package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EZShop implements EZShopInterface {

    // Users variable
    private List<User> users = new ArrayList<>();
    private User authenticatedUser;

    // Products variable
    private List<ProductType> products = new ArrayList<>();
    private List<EZSaleTransaction> transactions;
    private EZSaleTransaction openTransaction = null;
    // test for the id of the user
    int i = 1;

    // test for the id of the product
    int j = 1;

    @Override
    public void reset() {
    }

    @Override
    public Integer createUser(String username, String password, String role)
            throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
        if (username == null || username.equals(""))
            throw new InvalidUsernameException();
        if (password == null || password.equals(""))
            throw new InvalidPasswordException();
        if (authenticatedUser == null
                && (role == null || role.equals("") || !(role.equals("Administrator") || role.equals("ShopManager"))))
            throw new InvalidRoleException();

        Integer userID = -1;
        User user = null;

        // Create a new user
        try {
            user = new EZUser(EZShopDBManager.getInstance().getNextUserID(), username, password, role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Check if the username alredy exists in the database

        boolean usernameAlredyExists = false;
        try {
            usernameAlredyExists = EZShopDBManager.getInstance().searchUser(username);
            if(!usernameAlredyExists) {
                EZShopDBManager.getInstance().saveUser(user);
                userID = user.getId();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userID;
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if (authenticatedUser == null || !authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        if (id == null || id <= 0)
            throw new InvalidUserIdException();

        boolean deleted = false;
        try {
            deleted = EZShopDBManager.getInstance().deleteUser(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
        if (authenticatedUser == null || !authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();

        List<User> users = null;
        try {
            users = EZShopDBManager.getInstance().loadAllUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if (authenticatedUser == null || !authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        if (id == null || id <= 0)
            throw new InvalidUserIdException();

        User user = null;
        try {
            user = EZShopDBManager.getInstance().loadUser(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean updateUserRights(Integer id, String role)
            throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
        if (authenticatedUser == null || !authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        if (role == null || role.equals("") || !(role.equals("Cashier") || role.equals("Administrator") || role.equals("ShopManager")))
            throw new InvalidRoleException();
        if (id == null || id <= 0)
            throw new InvalidUserIdException();

        // Search for the user and if you find it update the rights
        boolean found = false;
        boolean modified = false;
        try {
            found = EZShopDBManager.getInstance().searchUser(id);
            if(found){
                EZShopDBManager.getInstance().updateUserRights(id, role);
                modified = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return modified;
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        if ( username == null || username.equals(""))
            throw new InvalidUsernameException();
        if (password == null || password.equals(""))
            throw new InvalidPasswordException();

        try {
            authenticatedUser = EZShopDBManager.getInstance().loadUser(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return authenticatedUser;
    }

    @Override
    public boolean logout() {
        authenticatedUser = null;
        return true;
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note)
            throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
            UnauthorizedException {

        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        if (description == null || description.equals(""))
            throw new InvalidProductDescriptionException();
        if (pricePerUnit <= 0)
            throw new InvalidPricePerUnitException();
        if(productCode == null || productCode == "" || !validBarCode(productCode))
            throw new InvalidProductCodeException();

        Integer productID = -1;
        ProductType product = null;

        // Create a new product
        try {
            product = new EZProductType(EZShopDBManager.getInstance().getNextProductID(),0, productCode, description, note, "", pricePerUnit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Search in there alredy is a product with the same barCode. If not, add the product
        boolean barCodeAlredyExists = false;
        try {
            barCodeAlredyExists = EZShopDBManager.getInstance().searchProductByBarCode(productCode);
            if(!barCodeAlredyExists){
                EZShopDBManager.getInstance().saveProduct(product);
                productID = product.getId();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return productID;
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
            throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
            InvalidPricePerUnitException, UnauthorizedException {

        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        if (newDescription == null || newDescription.equals(""))
            throw new InvalidProductDescriptionException();
        if (id == null || id <= 0)
            throw new InvalidProductIdException();
        if (newPrice <= 0)
            throw new InvalidPricePerUnitException();
        if(newCode == null || newCode == "" || !validBarCode(newCode))
            throw new InvalidProductCodeException();

        boolean found = false;
        try {
            found = EZShopDBManager.getInstance().searchProductById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean barCodeAlredyExists = false;
        try {
            barCodeAlredyExists = EZShopDBManager.getInstance().searchProductByBarCode(newCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // if the product exists and no other product has the same barCode
        ProductType product = null;
        boolean updated = false;
        if(found && !barCodeAlredyExists){
            try {
                product = EZShopDBManager.getInstance().loadProduct(id);
                product.setProductDescription(newDescription);
                product.setBarCode(newCode);
                product.setPricePerUnit(newPrice);
                product.setNote(newNote);
                EZShopDBManager.getInstance().updateProduct(product);
                updated = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        if (id == null || id <= 0)
            throw new InvalidProductIdException();

        boolean deleted = false;
        try {
            deleted = EZShopDBManager.getInstance().deleteProduct(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager") || authenticatedUser.getRole().equals("Cashier")))
            throw new UnauthorizedException();

        List<ProductType> products = null;
        try {
            products = EZShopDBManager.getInstance().loadAllProducts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode)
            throws InvalidProductCodeException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        if (barCode == null || barCode == "" || !validBarCode(barCode))
            throw new InvalidProductCodeException();

        ProductType product = null;
        try {
            EZShopDBManager.getInstance().loadProductByBarCode(barCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();

        List<ProductType> toReturn = new ArrayList<>();
        List<ProductType> products = null;

        // Get all products and search if you find product with the same description and add them to the list
        products = getAllProductTypes();
        for (ProductType product : products) {
            if (product.getProductDescription().equals(description))
                toReturn.add(product);
        }
        return toReturn;
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded)
            throws InvalidProductIdException, UnauthorizedException {

        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();

        if (productId == null || productId <= 0)
            throw new InvalidProductIdException();

        ProductType product = null;
        boolean updated = false;
        try {
            product = EZShopDBManager.getInstance().loadProduct(productId);
            if(product != null){
                // if the location is not empty it means that the product has a location
                if (!product.getLocation().equals("")) {
                    if (product.getQuantity() + toBeAdded > 0) {
                        product.setQuantity(product.getQuantity() + toBeAdded);
                        EZShopDBManager.getInstance().updateProduct(product);
                        updated = true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos)
            throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        if (productId == null || productId <= 0)
            throw new InvalidProductIdException();
        if (!newPos.matches("[0-9]*-[a-z,A-Z]*-[0-9]*"))
            throw new InvalidLocationException();

        boolean positionAlredyExists = false;
        try {
            positionAlredyExists = EZShopDBManager.getInstance().searchProductByLocation(newPos);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ProductType product = null;

        boolean foundAndUpdated = false;
        try {
            product = EZShopDBManager.getInstance().loadProduct(productId);
            if(product != null && !positionAlredyExists) {
                foundAndUpdated = true;
                product.setLocation(newPos);
                EZShopDBManager.getInstance().updateProduct(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return foundAndUpdated;
    }

    private boolean validBarCode(String barCode) {
        int sum = 0;
        int checksum = Character.getNumericValue(barCode.charAt(barCode.length() - 1));
        int offset = barCode.length() % 2;
        for (int i = 0; i < barCode.length() - 1; i++)
            sum += Integer.valueOf(barCode.charAt(i)) * ( (i + offset) % 2 == 0 ? 3 : 1 ) ;
        return checksum == 10 - (sum % 10);
    }

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException,
            InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        if (quantity <= 0)
            throw new InvalidQuantityException("Negative Quantity");
        if (pricePerUnit <= 0.0)
            throw new InvalidPricePerUnitException("Negative PPU");
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");
        if (productCode == null)
            throw new InvalidProductCodeException("Null ProductCode");
        if (!productCode.matches("[0-9]{12,14}"))
            throw new InvalidProductCodeException("Wrong ProductCode Lenght");
        if (!validBarCode(productCode))
            throw new InvalidProductCodeException("Failed Checksum");

        Integer nextOrderId = -1;
        Integer nextBalanceOperationId = -1;
        EZOrder newOrder = null;

        try {
            nextOrderId = EZShopDBManager.getInstance().getNextOrderID();
            nextBalanceOperationId = EZShopDBManager.getInstance().getNextBalanceOperationID();
            newOrder = new EZOrder(productCode, quantity, pricePerUnit);
            newOrder.setOrderId(nextOrderId);
            newOrder.setBalanceId(nextBalanceOperationId);
            EZShopDBManager.getInstance().saveOrder(newOrder);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return -1;
        }

        // if (nextOrderId < 0 || nextBalanceOperationId < 0)
        // return -1;
        // Superfluo?

        return nextOrderId;
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
            throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
            UnauthorizedException {

        Integer orderId = issueOrder(productCode, quantity, pricePerUnit);
        if (orderId.intValue() < 0)
            return -1;

        try {
            return !payOrder(orderId) ? -1 : orderId;
        } catch (InvalidOrderIdException e) {
            return -1; // Can't get here
        }

    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        if (orderId == null)
            throw new InvalidOrderIdException("Null Order ID");
        if (orderId <= 0)
            throw new InvalidOrderIdException("Negative (or zero) Order ID");
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        Order orderToPay = null;
        Integer orderBalanceOperationID = null;
        BalanceOperation balanceOperation = null;

        try {
            orderToPay = EZShopDBManager.getInstance().loadOrder(orderId);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        if (orderToPay == null)
            return false;
        if (!orderToPay.getStatus().matches("(ORDERED|ISSUED)"))
            return false;

        double orderPrice = orderToPay.getPricePerUnit() * orderToPay.getQuantity() * -1;

        try {
            orderToPay.setStatus("PAYED");
            orderBalanceOperationID = EZShopDBManager.getInstance().getNextBalanceOperationID();

            if (!recordBalanceUpdate(orderPrice))
                return false;

            balanceOperation = new EZBalanceOperation("DEBIT", orderPrice);
            balanceOperation.setBalanceId(orderBalanceOperationID);
            orderToPay.setBalanceId(orderBalanceOperationID);
            
            EZShopDBManager.getInstance().updateOrder(orderToPay);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId)
            throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        if (orderId == null)
            throw new InvalidOrderIdException("Null Order ID");
        if (orderId <= 0)
            throw new InvalidOrderIdException("Negative (or zero) Order ID");
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        Order myOrder = null;
        ProductType orderProduct = null;
        String productCode = "";

        try {
            myOrder = EZShopDBManager.getInstance().loadOrder(orderId);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        if (!myOrder.getStatus().matches("(ORDERED|COMPLETED)"))
            return false;

        productCode = myOrder.getProductCode();

        try {
            orderProduct = getProductTypeByBarCode(productCode);
        } catch (Exception e){
            // Can't happen...
        }

        if (orderProduct.getLocation() == null)
            throw new InvalidLocationException("Null Location");
        if (orderProduct.getLocation().matches(""))
            throw new InvalidLocationException("Empty");

        orderProduct.setQuantity(orderProduct.getQuantity() + myOrder.getQuantity());
        myOrder.setStatus("COMPLETED");

        try {
            EZShopDBManager.getInstance().updateProduct(orderProduct);
            EZShopDBManager.getInstance().updateOrder(myOrder);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        List<Order> orders = null;

        try {
            orders = EZShopDBManager.getInstance().loadAllOrders();
        } catch (Exception dbException) {
            dbException.printStackTrace();
            orders = new ArrayList<Order>();
        }

        return orders;
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
            throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
            UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return false;
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
        List<Customer> list = new ArrayList<Customer>();
        return list;
    }

    @Override
    public String createCard() throws UnauthorizedException {
        return null;
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId)
            throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
            throws InvalidCustomerCardException, UnauthorizedException {
        return false;
    }

    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {

        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
                )
            ) throw new UnauthorizedException();

        Integer id = null;

        try {
            id = EZShopDBManager.getInstance().getNextSaleID();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        EZSaleTransaction transaction = new EZSaleTransaction(id);
        this.openTransaction = transaction;

        return id;
    }

        // TODO: diminuisci la quantità su scaffali. ProductType quantity?

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || productCode == "" || validBarCode(productCode))
            throw new InvalidProductCodeException("product code is empty or null");
        if (amount < 0)
            throw new InvalidQuantityException("quantity is less than 0");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
                )
            ) throw new UnauthorizedException();

        if(transactionId != openTransaction.getTicketNumber() || !openTransaction.getStatus().equals("open")) return false;

        List<ProductType> products = null;

        try {
            products = EZShopDBManager.getInstance().loadAllProducts();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        for (ProductType p : products) {
            if (p.getBarCode().equals(productCode)) {

                if(p.getQuantity() < Integer.valueOf(amount)) return false;
                
                if(openTransaction.addProductToSale(productCode, p.getProductDescription(), p.getPricePerUnit(), Double.valueOf(0.0), amount)) {
                    p.setQuantity(p.getQuantity()-amount);
                    
                    try {
                        EZShopDBManager.getInstance().saveProduct(p);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
            
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {
        
        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || productCode == "" || validBarCode(productCode))
            throw new InvalidProductCodeException("product code is empty or null");
        if (amount < 0)
            throw new InvalidQuantityException("quantity is less than 0");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();

        if(transactionId != openTransaction.getTicketNumber() || !openTransaction.getStatus().equals("open")) return false;

        List<ProductType> products = null;

        try {
            products = EZShopDBManager.getInstance().loadAllProducts();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        for (ProductType p : products) {
            if (p.getBarCode().equals(productCode)) {

                TicketEntry e = openTransaction.getEntry(productCode);
                if(e != null) {

                    if(e.getAmount() < Integer.valueOf(amount)) return false;

                    openTransaction.deleteProductFromSale(productCode, amount);
                    p.setQuantity(p.getQuantity()+amount);

                    try {
                        EZShopDBManager.getInstance().saveProduct(p);
                    } catch (ClassNotFoundException er) {
                        er.printStackTrace();
                    } catch (SQLException er) {
                        er.printStackTrace();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || productCode == "" || validBarCode(productCode))
            throw new InvalidProductCodeException("product code is empty or null");
        if (discountRate < 0 || discountRate >= 1.0)
            throw new InvalidDiscountRateException("discount rate is less than 0 or greater than or equal to 1.00");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();

        if(transactionId != openTransaction.getTicketNumber() || !openTransaction.getStatus().equals("open")) return false;

        if(openTransaction.applyDiscountRateToProduct(productCode, discountRate)) return true;

        return false;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
            throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
        
        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (discountRate < 0 || discountRate >= 1.0)
            throw new InvalidDiscountRateException("discount rate is less than 0 or if it greater than or equal to 1.00");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();
        
        if(transactionId != openTransaction.getTicketNumber() || !(openTransaction.getStatus().equals("open") || openTransaction.getStatus().equals("closed"))) return false;

        openTransaction.setDiscountRate(Double.valueOf(discountRate));
        return true;

    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        
        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();

        if(transactionId == openTransaction.getTicketNumber()) {
            return openTransaction.computePoints().intValue();
        }
        else {
            List<EZSaleTransaction> st = null;

            try {
                st = EZShopDBManager.getInstance().loadAllSales();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return -1;
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }

            for(EZSaleTransaction s : st) {
                if(transactionId == s.getTicketNumber()) {
                    return s.computePoints().intValue();
                }

            }
        }
        
        return -1;
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
        throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();

        if(transactionId != openTransaction.getTicketNumber() || openTransaction.getStatus().equals("closed")) return false;

        if(openTransaction.endSaleTransaction()){
            try {
                EZShopDBManager.getInstance().saveSale(openTransaction);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    
        return false;
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (saleNumber == null || saleNumber <= 0)
        throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();

        if(saleNumber == openTransaction.getTicketNumber()) {
            if(!openTransaction.getStatus().equals("payed")) {
                openTransaction = null;
                return true;
            }
            else {
                return false;
            }
        }
        else {
            List<EZSaleTransaction> st = null;

            try {
                st = EZShopDBManager.getInstance().loadAllSales();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for(EZSaleTransaction s : st) {
                if(saleNumber == s.getTicketNumber()) {
                    if(!s.getStatus().equals("payed")) {
                        try {
                            EZShopDBManager.getInstance().deleteSale(saleNumber);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            return false;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                }

            }
        }

        return false;
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
        throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null
            && (    authenticatedUser.getRole() == null || 
                    authenticatedUser.getRole().equals("") || 
                    !(  authenticatedUser.getRole().equals("Administrator") || 
                        authenticatedUser.getRole().equals("ShopManager") ||
                        authenticatedUser.getRole().equals("Cashier")
                    )
            )
        ) throw new UnauthorizedException();
        
        if(transactionId == openTransaction.getTicketNumber()) {
            if(!openTransaction.getStatus().equals("open")) {
                return openTransaction;
            }
            else {
                return null;
            }
        }
        else {
            List<EZSaleTransaction> st = null;

            try {
                st = EZShopDBManager.getInstance().loadAllSales();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

            for(EZSaleTransaction s : st) {
                if(transactionId == s.getTicketNumber()) {
                    if(!s.getStatus().equals("open")) {
                        return s;
                    }
                    else {
                        return null;
                    }
                }

            }
        }

        return null;
    }

    @Override
    public Integer startReturnTransaction(Integer saleNumber)
            throws /* InvalidTicketNumberException, */InvalidTransactionIdException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException,
            InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit)
            throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId)
            throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash)
            throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return false;
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return 0;
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        return EZAccountBook.getInstance().recordBalance(toBeAdded);
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        if (from != null && to != null)
            if (from.isAfter(to))
                return getCreditsAndDebits(to, from);

        return EZAccountBook.getInstance().getBalanceOperationsList().stream()
                .filter(bo -> from == null ? true : (bo.getDate().isAfter(from) || bo.getDate().isEqual(from)))
                .filter(bo -> to == null ? true : (bo.getDate().isBefore(to) || bo.getDate().isEqual(to)))
                .collect(Collectors.toList());
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        return EZAccountBook.getInstance().getBalance();
    }
}
