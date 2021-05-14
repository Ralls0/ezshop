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
    private EZSaleTransaction openTransaction = null;
    private EZReturnTransaction openReturnTransaction = null;
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
        if (authenticatedUser == null)
            throw new InvalidRoleException();
        if (!role.matches("(Administrator|ShopManager)"))
            throw new InvalidRoleException();

        Integer userID = -1;
        User user = null;

        try {
            user = new EZUser(EZShopDBManager.getInstance().getNextUserID(), username, password, role);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        try {
            if (EZShopDBManager.getInstance().searchUser(username)) // TODO: rename searchUser into userExists?
                return -1;
            EZShopDBManager.getInstance().saveUser(user);
            userID = user.getId();
        } catch (Exception e) {
            e.printStackTrace();
            userID = -1;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception dbException) {
            dbException.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean updateUserRights(Integer id, String role)
            throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
        if (authenticatedUser == null || !authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        if (role == null || !role.matches("(ShopManager|Administrator|Cashier)"))
            throw new InvalidRoleException();
        if (id == null || id <= 0)
            throw new InvalidUserIdException();

        boolean found = false;
        boolean modified = false;

        try {
            found = EZShopDBManager.getInstance().searchUser(id);

            if (found) {
                EZShopDBManager.getInstance().updateUserRights(id, role);
                modified = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            modified = false;
        }

        return modified;
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        if (username == null || username.equals(""))
            throw new InvalidUsernameException();
        if (password == null || password.equals(""))
            throw new InvalidPasswordException();

        try {
            authenticatedUser = EZShopDBManager.getInstance().loadUser(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException();
        if (description == null || description.equals(""))
            throw new InvalidProductDescriptionException();
        if (pricePerUnit <= 0)
            throw new InvalidPricePerUnitException();
        if (productCode == null || productCode.equals("") || !validBarCode(productCode))
            throw new InvalidProductCodeException();

        Integer productID = -1;
        ProductType product = null;

        try {
            product = new EZProductType(EZShopDBManager.getInstance().getNextProductID(), 0, productCode, description,
                    note, "", pricePerUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean barCodeAlredyExists = false;
        try {
            barCodeAlredyExists = EZShopDBManager.getInstance().searchProductByBarCode(productCode);
            if (!barCodeAlredyExists) {
                EZShopDBManager.getInstance().saveProduct(product);
                productID = product.getId();
            }
        } catch (Exception e) {
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException();
        if (newDescription == null || newDescription.equals(""))
            throw new InvalidProductDescriptionException();
        if (id == null || id <= 0)
            throw new InvalidProductIdException();
        if (newPrice <= 0)
            throw new InvalidPricePerUnitException();
        if (newCode == null || newCode.equals("") || !validBarCode(newCode))
            throw new InvalidProductCodeException();

        boolean found = false;
        try {
            found = EZShopDBManager.getInstance().searchProductById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ProductType product = null;

        boolean barCodeAlredyExists = false;
        try {
            barCodeAlredyExists = EZShopDBManager.getInstance().searchProductByBarCode(newCode);
            product = EZShopDBManager.getInstance().loadProduct(id);
            if (product.getBarCode().equals(newCode))
                barCodeAlredyExists = false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // if the product exists and no other product has the same barCode
        boolean updated = false;
        if (found && !barCodeAlredyExists) {
            try {
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException();
        if (barCode == null || barCode.equals("") || !validBarCode(barCode))
            throw new InvalidProductCodeException();

        ProductType product = null;
        try {
            product = EZShopDBManager.getInstance().loadProductByBarCode(barCode);
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException();

        List<ProductType> toReturn = new ArrayList<>();
        List<ProductType> products = null;

        // Get all products and search if you find product with the same description and
        // add them to the list
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException();

        if (productId == null || productId <= 0)
            throw new InvalidProductIdException();

        ProductType product = null;
        boolean updated = false;
        try {
            product = EZShopDBManager.getInstance().loadProduct(productId);
            if (product != null) {
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
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException();
        if (productId == null || productId <= 0)
            throw new InvalidProductIdException();
        if (!newPos.matches("[0-9]{1,}-[a-zA-Z]{1,}-[0-9]{1,}"))
            throw new InvalidLocationException();

        boolean positionAlredyExists = false;
        ProductType product = null;
        try {
            positionAlredyExists = EZShopDBManager.getInstance().searchProductByLocation(newPos);
            product = EZShopDBManager.getInstance().loadProduct(productId);
            if (product.getLocation().equals(newPos))
                positionAlredyExists = false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean foundAndUpdated = false;
        try {
            if (product != null && !positionAlredyExists) {
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
            sum += Character.getNumericValue(barCode.charAt(i)) * ((i + offset) % 2 == 0 ? 3 : 1);
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

            // TODO: balance

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
        } catch (Exception e) {
            // Can't happen...
        }

        // FIXME: regex
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
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();
        if (customerName == null || customerName.equals(""))
            throw new InvalidCustomerNameException();

        Customer customer = null;
        boolean alredyExists = false;
        Integer customerID = -1;
        try {
            alredyExists = EZShopDBManager.getInstance().searchCustomerByName(customerName);
            if (!alredyExists) {
                customer = new EZCustomer(EZShopDBManager.getInstance().getNextCustomerID(), customerName, "", 0);
                EZShopDBManager.getInstance().saveCustomer(customer);
                customerID = customer.getId();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customerID;
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
            throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
            UnauthorizedException {

        if (newCustomerCard.length() != 10 && !(newCustomerCard.equals("") && newCustomerCard.matches("[0-9]*")))
            throw new InvalidCustomerCardException();
        if (newCustomerName == null || newCustomerName.equals(""))
            throw new InvalidCustomerNameException();
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();
        if (id == null || id <= 0)
            throw new InvalidCustomerIdException();

        Customer customer = null;
        try {
            customer = EZShopDBManager.getInstance().loadCustomer(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean cardAlredyExists = false;
        boolean updated = false;
        try {
            if (!newCustomerCard.equals("")) {
                cardAlredyExists = EZShopDBManager.getInstance().searchCustomerByCard(newCustomerCard);
                if (cardAlredyExists && customer.getCustomerCard().equals(newCustomerCard)) {
                    // I'm modifyig the customer with the same card number, so I only need to update
                    // the name
                    EZShopDBManager.getInstance().updateCustomer(id, newCustomerName, customer.getCustomerCard());
                    updated = true;
                }
            }
            if (!cardAlredyExists) {
                if (newCustomerCard != null) {
                    if (newCustomerCard.equals("")) {
                        EZShopDBManager.getInstance().updateCustomer(id, newCustomerName, "");
                    } else {
                        EZShopDBManager.getInstance().updateCustomer(id, newCustomerName, newCustomerCard);
                    }
                    updated = true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;

    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();
        if (id == null || id <= 0)
            throw new InvalidCustomerIdException();

        boolean existsAndDeleted = false;
        try {
            existsAndDeleted = EZShopDBManager.getInstance().searchCustomerById(id);
            if (existsAndDeleted)
                EZShopDBManager.getInstance().deleteCustomer(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return existsAndDeleted;
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();
        if (id == null || id <= 0)
            throw new InvalidCustomerIdException();

        Customer customer = null;
        try {
            customer = EZShopDBManager.getInstance().loadCustomer(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        List<Customer> customers = new ArrayList<Customer>();
        try {
            customers = EZShopDBManager.getInstance().loadAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public String createCard() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        // TODO: finire

        return "1111111111";
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId)
            throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();
        if (customerId == null || customerId <= 0)
            throw new InvalidCustomerIdException();
        if (!customerCard.matches("[0-9]{10}"))
            throw new InvalidCustomerCardException();

        boolean cardAlredyExists = false;
        try {
            cardAlredyExists = EZShopDBManager.getInstance().searchCustomerByCard(customerCard);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Customer customer = null;
        try {
            customer = EZShopDBManager.getInstance().loadCustomer(customerId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean updated = false;
        try {
            if (!cardAlredyExists && customer != null) {
                EZShopDBManager.getInstance().updateCustomer(customerId, customer.getCustomerName(), customerCard);
                updated = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
            throws InvalidCustomerCardException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();
        if (customerCard.length() != 10 && !(customerCard.equals("") && customerCard.matches("[0-9]*")))
            throw new InvalidCustomerCardException();

        Customer customer = null;
        boolean cardAlredyExists = false;
        boolean updated = false;
        try {
            cardAlredyExists = EZShopDBManager.getInstance().searchCustomerByCard(customerCard);
            if (cardAlredyExists) {
                customer = EZShopDBManager.getInstance().loadCustomer(customerCard);
                if (pointsToBeAdded > 0) {
                    // TODO: fix per accettare anche punti negativi
                    customer.setPoints(customer.getPoints() + pointsToBeAdded);
                    updated = true;
                    EZShopDBManager.getInstance().updateCustomer(customer.getId(), customer.getCustomerName(),
                            customer.getCustomerCard());
                    // TODO: mancano i points
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
    public Integer startSaleTransaction() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        Integer id = null;

        try {
            id = EZShopDBManager.getInstance().getNextSaleID();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return -1;
        }

        EZSaleTransaction transaction = new EZSaleTransaction(id);
        this.openTransaction = transaction;

        return id;
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || !productCode.matches("[0-9]{12,14}") || !validBarCode(productCode))
            throw new InvalidProductCodeException("product code is empty or null");
        if (amount < 0.0)
            throw new InvalidQuantityException("quantity is less than 0");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openTransaction == null || transactionId != openTransaction.getTicketNumber()
                || !openTransaction.getStatus().equals("open"))
            return false;

        List<ProductType> products = null;

        try {
            products = EZShopDBManager.getInstance().loadAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        ProductType product = products.stream().filter(p -> p.getBarCode().equals(productCode)).findFirst()
                .orElse(null);
        Boolean branch;

        if (product == null)
            return false;
        if (product.getQuantity() < amount)
            return false;

        branch = openTransaction.addProductToSale(productCode, product.getProductDescription(),
                product.getPricePerUnit(), 0.0, amount);

        if (!branch)
            return false;

        try {
            product.setQuantity(product.getQuantity() - amount);
            EZShopDBManager.getInstance().updateProduct(product);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || !productCode.matches("[0-9]{12,14}") || !validBarCode(productCode))
            throw new InvalidProductCodeException("product code is empty or null");
        if (amount < 0)
            throw new InvalidQuantityException("quantity is less than 0");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openTransaction == null || transactionId != openTransaction.getTicketNumber()
                || !openTransaction.getStatus().equals("open"))
            return false;

        List<ProductType> products = null;
        ProductType product;
        TicketEntry ticketEntry;

        try {
            products = EZShopDBManager.getInstance().loadAllProducts();
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        product = products.stream().filter(p -> p.getBarCode().equals(productCode)).findFirst().get();
        if (product == null)
            return false;

        ticketEntry = openTransaction.getEntry(productCode);
        if (ticketEntry == null)
            return false;
        if (ticketEntry.getAmount() < amount)
            return false;

        openTransaction.deleteProductFromSale(productCode, amount);
        product.setQuantity(product.getQuantity() + amount);

        try {
            EZShopDBManager.getInstance().updateProduct(product);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || !productCode.matches("[0-9]{12,14}") || !validBarCode(productCode))
            throw new InvalidProductCodeException("product code is invalid");
        if (discountRate < 0 || discountRate >= 1.0)
            throw new InvalidDiscountRateException("discount rate is less than 0 or greater than or equal to 1.00");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openTransaction == null || transactionId != openTransaction.getTicketNumber()
                || !openTransaction.getStatus().equals("open"))
            return false;

        if (openTransaction.applyDiscountRateToProduct(productCode, discountRate))
            return true;

        return false;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
            throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (discountRate < 0.0 || discountRate >= 1.0)
            throw new InvalidDiscountRateException(
                    "discount rate is less than 0 or if it greater than or equal to 1.00");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openTransaction == null || transactionId != openTransaction.getTicketNumber()
                || !(openTransaction.getStatus().equals("open") || openTransaction.getStatus().equals("closed")))
            return false;

        openTransaction.setDiscountRate(Double.valueOf(discountRate));
        return true;

    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        List<EZSaleTransaction> st = null;

        if (openTransaction != null && transactionId == openTransaction.getTicketNumber())
            return openTransaction.computePoints().intValue();

        try {
            st = EZShopDBManager.getInstance().loadAllSales();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return st.stream().filter(s -> s.getTicketNumber() == transactionId).findFirst().map(s -> s.computePoints())
                .orElse(-1);

    }

    @Override
    public boolean endSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openTransaction == null || transactionId != openTransaction.getTicketNumber()
                || openTransaction.getStatus().equals("closed"))
            return false;

        if (!openTransaction.endSaleTransaction())
            return false;

        try {
            EZShopDBManager.getInstance().saveSale(openTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (saleNumber == null || saleNumber <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        ProductType product = null;

        if (openTransaction != null && saleNumber == openTransaction.getTicketNumber()) {
            if (!openTransaction.getStatus().equals("payed")) {
                if (saleNumber == openTransaction.getTicketNumber()) {
                    try {
                        for (TicketEntry p : openTransaction.getEntries()) {
                            try {
                                product = this.getProductTypeByBarCode(p.getBarCode());
                                product.setQuantity(product.getQuantity() + p.getAmount());
                                EZShopDBManager.getInstance().updateProduct(product);
                            } catch (InvalidProductCodeException e) {
                                e.printStackTrace();
                                return false;
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                                return false;
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
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

                openTransaction = null;
                return true;
            } else {
                return false;
            }
        } else {
            List<EZSaleTransaction> st = null;

            try {
                st = EZShopDBManager.getInstance().loadAllSales();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (EZSaleTransaction s : st) {
                if (saleNumber == s.getTicketNumber()) {
                    try {
                        for (TicketEntry p : s.getEntries()) {
                            try {
                                product = this.getProductTypeByBarCode(p.getBarCode());
                                product.setQuantity(product.getQuantity() + p.getAmount());
                                EZShopDBManager.getInstance().updateProduct(product);
                            } catch (InvalidProductCodeException e) {
                                e.printStackTrace();
                                return false;
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                                return false;
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
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

            }
        }

        return false;
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        List<EZSaleTransaction> st = null;

        if (openTransaction != null && transactionId == openTransaction.getTicketNumber()) {
            if (!openTransaction.getStatus().equals("open"))
                return openTransaction;
            return null;
        }

        try {
            st = EZShopDBManager.getInstance().loadAllSales();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return st.stream().filter(s -> transactionId == s.getTicketNumber()).findFirst().orElse(null);
    }

    // FIXME: Commento?
    @Override
    public Integer startReturnTransaction(Integer saleNumber)
            throws /* InvalidTicketNumberException, */InvalidTransactionIdException, UnauthorizedException {

        if (saleNumber == null || saleNumber <= 0)
            throw new InvalidTransactionIdException("sale id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openReturnTransaction != null)
            return -1;

        EZSaleTransaction saleT = null;
        Integer id = -1;

        try {
            saleT = EZShopDBManager.getInstance().loadSale(saleNumber);
            if (saleT == null || !saleT.getStatus().equals("payed")) //FIXME: Sicuri?
                return -1;

            id = EZShopDBManager.getInstance().getNextReturnID();
            EZReturnTransaction rTransaction = new EZReturnTransaction(saleNumber, id);
            this.openReturnTransaction = rTransaction;
            return id;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException,
            InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        if (returnId == null || returnId <= 0)
            throw new InvalidTransactionIdException("return id less than or equal to 0 or it is null");
        if (productCode == null || productCode.equals("") || !validBarCode(productCode))
            throw new InvalidProductCodeException("product code is empty or null");
        if (amount < 0)
            throw new InvalidQuantityException("quantity is less than 0");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openReturnTransaction == null || returnId != openReturnTransaction.getReturnId())
            return false;

        SaleTransaction transaction = null;
        Boolean returnValue = false;

        try {
            transaction = EZShopDBManager.getInstance().loadSale(openReturnTransaction.getTransactionId());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (transaction == null)
            return false;

        /*
         * for (TicketEntry entry : transaction.getEntries()) { if
         * (entry.getBarCode().equals(productCode)) { if (amount <= entry.getAmount()) {
         * if (openReturnTransaction.addProductReturned((EZTicketEntry) entry)) {
         * openReturnTransaction.setDiscountRate(transaction.getDiscountRate()); return
         * true; } } return false; } } For easy rollback
         */

        returnValue = transaction.getEntries().stream().filter(e -> e.getBarCode().equals(productCode))
                .filter(e -> e.getAmount() > amount)
                .anyMatch(e -> openReturnTransaction.addProductReturned((EZTicketEntry) e));

        if (returnValue)
            openReturnTransaction.setDiscountRate(transaction.getDiscountRate());

        return returnValue;

    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit)
            throws InvalidTransactionIdException, UnauthorizedException {
        if (returnId == null || returnId <= 0)
            throw new InvalidTransactionIdException("return id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openReturnTransaction == null || returnId != openReturnTransaction.getReturnId())
            return false;

        EZSaleTransaction transaction = null;
        ProductType product = null;
        Boolean returnValue = false;

        try {
            transaction = EZShopDBManager.getInstance().loadSale(openReturnTransaction.getTransactionId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        openReturnTransaction.setCommit(commit);

        if (!commit) {
            openReturnTransaction = null;
            return false;
        }

        try {
            for (EZTicketEntry p : openReturnTransaction.getProducts()) {
                transaction.deleteProductFromSale(p.getBarCode(), p.getAmount());
                product = this.getProductTypeByBarCode(p.getBarCode());
                product.setQuantity(product.getQuantity() + p.getAmount());
                EZShopDBManager.getInstance().updateProduct(product);
            }

            openReturnTransaction.setStatus("closed");
            EZShopDBManager.getInstance().updateSale(transaction);
            EZShopDBManager.getInstance().saveReturn(openReturnTransaction);

            returnValue = true;
        } catch (Exception e) {
            returnValue = false;
        }

        return returnValue;
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (returnId == null || returnId <= 0)
            throw new InvalidTransactionIdException("return id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openReturnTransaction == null || returnId != openReturnTransaction.getReturnId()
                || openReturnTransaction.getStatus().equals("open"))
            return false;

        EZSaleTransaction transaction = null;
        ProductType product = null;

        try {
            transaction = EZShopDBManager.getInstance().loadSale(openReturnTransaction.getTransactionId());

            for (EZTicketEntry p : openReturnTransaction.getProducts()) {
                transaction.addProductToSale(p.getBarCode(), p.getProductDescription(), p.getPricePerUnit(),
                        p.getDiscountRate(), p.getAmount());
                product = getProductTypeByBarCode(p.getBarCode());
                product.setQuantity(product.getQuantity() - p.getAmount());
                EZShopDBManager.getInstance().updateProduct(product);
            }

            EZShopDBManager.getInstance().updateSale(transaction);
            EZShopDBManager.getInstance().deleteReturnTransaction(openReturnTransaction.getReturnId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        openReturnTransaction = null;
        return true;
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash)
            throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {

        if (ticketNumber == null || ticketNumber <= 0)
            throw new InvalidTransactionIdException("return id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (cash <= 0.0)
            throw new InvalidPaymentException("Cash is less than or equal to 0.0");
        if (openTransaction == null || ticketNumber != openTransaction.getTicketNumber()
                || !openTransaction.getStatus().equals("closed"))
            return -1;

        double paym = openTransaction.receiveCashPayment(cash);
        Integer orderBalanceOperationID = null;
        BalanceOperation balanceOperation = null;

        if (paym < 0.0)
            return -1;

        try {
            if (EZShopDBManager.getInstance().updateSale(openTransaction))
                return -1;

            orderBalanceOperationID = EZShopDBManager.getInstance().getNextBalanceOperationID();
            balanceOperation = new EZBalanceOperation("CREDIT", openTransaction.getPrice());
            balanceOperation.setBalanceId(orderBalanceOperationID);
            EZShopDBManager.getInstance().saveBalanceOperation(balanceOperation);
            openTransaction = null;
            return paym;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {

        if (ticketNumber == null || ticketNumber <= 0)
            throw new InvalidTransactionIdException("ticket number less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (creditCard == null || creditCard.equals("") || !EZSaleTransaction.validLuhnAlgorithm(creditCard))
            throw new InvalidCreditCardException("Credit card not valid");

        if (openTransaction == null || ticketNumber != openTransaction.getTicketNumber()
                || !openTransaction.getStatus().equals("closed"))
            return false;

        try {
            if (!CreditCardCircuit.getInstance().isCardPresent(creditCard))
                return false;
            if (!CreditCardCircuit.getInstance().hasEnoughBalance(creditCard, openTransaction.getPrice()))
                return false;
            if (CreditCardCircuit.getInstance().pay(creditCard, openTransaction.getPrice())) {
                openTransaction.receiveCreditCardPayment(creditCard);
                if (EZShopDBManager.getInstance().updateSale(openTransaction)) {
                    Integer orderBalanceOperationID = null;
                    BalanceOperation balanceOperation = null;
                    orderBalanceOperationID = EZShopDBManager.getInstance().getNextBalanceOperationID();
                    balanceOperation = new EZBalanceOperation("CREDIT", openTransaction.getPrice());
                    balanceOperation.setBalanceId(orderBalanceOperationID);
                    EZShopDBManager.getInstance().saveBalanceOperation(balanceOperation);
                    openTransaction = null;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {

        if (returnId == null || returnId <= 0)
            throw new InvalidTransactionIdException("return id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (openReturnTransaction == null || returnId != openReturnTransaction.getReturnId()
                || !openReturnTransaction.getStatus().equals("closed"))
            return -1;

        double retm = openReturnTransaction.getPrice();

        if (retm != -1) {
            try {
                EZShopDBManager.getInstance().updateReturnStatus(openReturnTransaction.getReturnId(), "payed");
                Integer orderBalanceOperationID = null;
                BalanceOperation balanceOperation = null;
                orderBalanceOperationID = EZShopDBManager.getInstance().getNextBalanceOperationID();
                balanceOperation = new EZBalanceOperation("DEBIT", openReturnTransaction.getPrice());
                balanceOperation.setBalanceId(orderBalanceOperationID);
                EZShopDBManager.getInstance().saveBalanceOperation(balanceOperation);
                openReturnTransaction = null;
                return retm;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        return -1;

    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {

        if (returnId == null || returnId <= 0)
            throw new InvalidTransactionIdException("return Id less than or equal to 0 or it is null");
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager|Cashier)"))
            throw new UnauthorizedException();

        if (creditCard == null || creditCard.equals("") || !EZSaleTransaction.validLuhnAlgorithm(creditCard))
            throw new InvalidCreditCardException("Credit card not valid");

        if (openReturnTransaction == null || returnId != openReturnTransaction.getReturnId()
                || !openReturnTransaction.getStatus().equals("closed"))
            return -1;

        double retm = openReturnTransaction.getPrice();

        if (retm != -1) {
            try {
                if (!CreditCardCircuit.getInstance().isCardPresent(creditCard))
                    return -1;
                if (CreditCardCircuit.getInstance().refund(creditCard, openReturnTransaction.getPrice())) {
                    EZShopDBManager.getInstance().updateReturnStatus(openReturnTransaction.getReturnId(), "payed");
                    Integer orderBalanceOperationID = null;
                    BalanceOperation balanceOperation = null;
                    orderBalanceOperationID = EZShopDBManager.getInstance().getNextBalanceOperationID();
                    balanceOperation = new EZBalanceOperation("DEBIT", openReturnTransaction.getPrice());
                    balanceOperation.setBalanceId(orderBalanceOperationID);
                    EZShopDBManager.getInstance().saveBalanceOperation(balanceOperation);
                    openReturnTransaction = null;
                    return retm;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
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
