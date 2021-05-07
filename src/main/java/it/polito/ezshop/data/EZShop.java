package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EZShop implements EZShopInterface {

    // private HashMap<Integer, Order> orderMap;
    private EZAccountBook accountBook;
    // Users variable
    private List<User> users = new ArrayList<>();
    private User authenticatedUser;

    // Products variable
    private List<ProductType> products = new ArrayList<>();
    private List<EZSaleTransaction> transactions;
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
        if (username.equals("") || username == null)
            throw new InvalidUsernameException();
        if (password.equals("") || password == null)
            throw new InvalidPasswordException();
        if (authenticatedUser == null
                && (role.equals("") || role == null || !(role.equals("Administrator") || role.equals("ShopManager"))))
            throw new InvalidRoleException();

        Integer userID = -1;
        try {
            EZUser user = new EZUser(EZShopDBManager.getInstance().getNextUserID(), username, password, role);
            EZShopDBManager.getInstance().saveUser(user);
            userID = user.getId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userID;
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if (!authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        boolean foundAndDeleted = false;
        for (User user : users) {
            if (user.getId().equals(id)) {
                foundAndDeleted = true;
                users.remove(user);
                break;
            }
        }
        if (!foundAndDeleted) {
            throw new InvalidUserIdException();
        }
        return foundAndDeleted;
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
        return users;
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if (!authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        boolean found = false;
        User toReturn = null;
        for (User user : users) {
            if (user.getId().equals(id)) {
                found = true;
                toReturn = user;
            }
        }
        if (!found) {
            throw new InvalidUserIdException();
        }
        return toReturn;
    }

    @Override
    public boolean updateUserRights(Integer id, String role)
            throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
        if (!authenticatedUser.getRole().equals("Administrator"))
            throw new UnauthorizedException();
        if (role.equals("") || role == null
                || !(role.equals("Cashier") || role.equals("Administrator") || role.equals("ShopManager")))
            throw new InvalidRoleException();
        boolean foundAndModified = false;
        for (User user : users) {
            if (user.getId().equals(id)) {
                foundAndModified = true;
                user.setRole(role);
            }
        }
        if (!foundAndModified) {
            throw new InvalidUserIdException();
        }

        return foundAndModified;
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        if (username.equals("") || username == null)
            throw new InvalidUsernameException();
        if (password.equals("") || password == null)
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
        if (description == "" || description == null)
            throw new InvalidProductDescriptionException();
        if (pricePerUnit <= 0)
            throw new InvalidPricePerUnitException();

        // add the if to check if the barcode is valid
        EZProductType product = new EZProductType(j, 0, productCode, description, note, "", pricePerUnit);

        if (authenticatedUser == null) {

            products.add(product);
            j++;
        }

        return product.getId();
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
            throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
            InvalidPricePerUnitException, UnauthorizedException {

        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        if (newDescription == "" || newDescription == null)
            throw new InvalidProductDescriptionException();
        if (id <= 0 || id == null)
            throw new InvalidProductIdException();
        if (newPrice <= 0)
            throw new InvalidPricePerUnitException();

        // add the if to check if the barcode is valid

        boolean foundAndModified = false;
        for (ProductType product : products) {
            if (product.getId().equals(id)) {
                foundAndModified = true;
                product.setBarCode(newCode);
                product.setNote(newNote);
                product.setPricePerUnit(newPrice);
                product.setProductDescription(newDescription);
            }
        }
        return foundAndModified;
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        boolean foundAndDeleted = false;
        for (ProductType product : products) {
            if (product.getId().equals(id)) {
                foundAndDeleted = true;
                products.remove(product);
                break;
            }
        }
        return foundAndDeleted;
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        return products;
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode)
            throws InvalidProductCodeException, UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();
        ProductType toReturn = null;
        for (ProductType product : products) {
            if (product.getBarCode().equals(barCode))
                toReturn = product;
        }

        if (toReturn == null) {
            throw new InvalidProductCodeException();
        }
        return toReturn;
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException();
        if (!(authenticatedUser.getRole().equals("Administrator") || authenticatedUser.getRole().equals("ShopManager")))
            throw new UnauthorizedException();

        List<ProductType> toReturn = new ArrayList<>();
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

        if (productId <= 0)
            throw new InvalidProductIdException();

        for (ProductType product : products) {
            if (product.getId().equals(productId)) {
                if (!product.getLocation().equals("")) {
                    if (product.getQuantity() + toBeAdded < 0)
                        product.setQuantity(0);
                    else
                        product.setQuantity(product.getQuantity() + toBeAdded);
                }
            }
        }
        return false;
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos)
            throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        // TO DO
        return false;
    }

    private boolean validBarCode(String barCode) {
        int sum = 0;
        int checksum = Character.getNumericValue(barCode.charAt(barCode.length() - 1));
        int offset = barCode.length() % 2;
        for (int i = 0; i < barCode.length() - 1; i++)
            sum += Integer.valueOf(barCode.charAt(i)) * (i + offset) % 2 == 0 ? 3 : 1;
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

        //if (nextOrderId < 0 || nextBalanceOperationId < 0)
        //    return -1;
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

        Order orderToPay = orderMap.get(orderId);

        if (orderToPay == null)
            return false;
        if (!orderToPay.getStatus().matches("(ORDERED|ISSUED)"))
            return false;

        double orderPrice = orderToPay.getPricePerUnit() * orderToPay.getQuantity() * -1;

        if (!recordBalanceUpdate(orderPrice))
            return false;

        orderToPay.setStatus("PAYED"); // Sarebbe Paid...
        orderToPay.setBalanceId(accountBook.getCurrentBalanceOperationID());
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
        Order myOrder = orderMap.get(orderId);
        // TODO: InvalidLocationException if no valid location CC: Giovanni
        if (!myOrder.getStatus().matches("(ORDERED|COMPLETED)"))
            return false;
        // TODO: Increment Product Quantity CC: Giovanni
        // Integer quantity = myOrder.getQuantity()
        myOrder.setStatus("COMPLETED");
        return true;
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        if (authenticatedUser == null)
            throw new UnauthorizedException("No User Logged In");
        if (!authenticatedUser.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");
        if (orderMap == null)
            initOrderMap();
        // TODO: fix
        return orderMap.values().stream().collect(Collectors.toList());
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

    // TODO: Sistema
    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {
        // TODO: controlli ed eccezioni
        EZSaleTransaction transaction = new EZSaleTransaction(1); // TODO
        this.transactions.add(transaction);
        // TODO: aggiornare db
        return transaction.getTicketNumber();
    }

        // TODO: diminuisci la quantità su scaffali. ProductType quantity?

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || productCode == "")
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

        for (EZSaleTransaction t : transactions) {
            if (transactionId == t.getTicketNumber()) {
                for (ProductType p : products) {
                    if (p.getBarCode().equals(productCode)) {
                        t.addProductToSale(productCode, "", Double.valueOf(0.0), Double.valueOf(0.0), amount); // TODO: aggiungi: productDescription, pricePerUnit e discountRate = 0; dopo averlo cercato
                        return true;
                    }
                }
                throw new InvalidProductCodeException("product code is invalid");
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
        if (productCode == null || productCode == "")
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

        return false;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
            UnauthorizedException {

        if (transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("transaction id less than or equal to 0 or it is null");
        if (productCode == null || productCode == "")
            throw new InvalidProductCodeException("product code is empty or null");
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
        
        return false;
    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber)
            throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
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

        return accountBook.recordBalance(toBeAdded);
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

        if (accountBook == null)
            accountBook = EZAccountBook.loadAccountBook();

        return accountBook.getAccountBookEntries().values().stream()
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

        return accountBook.getBalance();
    }
}
