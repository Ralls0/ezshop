package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EZShop implements EZShopInterface {

    EzUser user;
    HashMap<Integer, Order> orderMap;
    HashMap<Integer, BalanceOperation> accountBook;
    double shopBalance;

    @Override
    public void reset() {

    }

    @Override
    public Integer createUser(String username, String password, String role)
            throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
        user = new EzUser(0, username, password, role);
        // select user count from db
        // new User...

        // return user

        return 0;
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        return false;
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
        return null;
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateUserRights(Integer id, String role)
            throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
        return false;
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        return user;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note)
            throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
            UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
            throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
            InvalidPricePerUnitException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
        return false;
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
        List<ProductType> list = new ArrayList<>();
        return list;
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode)
            throws InvalidProductCodeException, UnauthorizedException {
        return null;
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded)
            throws InvalidProductIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos)
            throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        return false;
    }

    private boolean initOrderMap() {
        //TODO: Laod From DB...
        orderMap = new HashMap<Integer, Order>();
        return true;
    }

    private boolean validBarCode(String barCode) {
        int sum = 0;
        int checksum = Integer.valueOf(barCode.charAt(barCode.length()));
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
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");
        if (productCode == null)
            throw new InvalidProductCodeException("Null ProductCode");
        if (!productCode.matches("[0-9]{12,14}"))
            throw new InvalidProductCodeException("Wrong Format");
        if (!validBarCode(productCode))
            throw new InvalidProductCodeException("Failed Checksum");

        Integer nextOrderId = 1; // TODO: db.getNextOrderId();
        if (nextOrderId < 0)
            return Integer.valueOf(-1);

        // TODO: DEBIT or ORDER?
        EZBalanceOperation balanceOperation = new EZBalanceOperation("DEBIT", pricePerUnit * quantity);
        EZOrder newOrder = new EZOrder(productCode, quantity, pricePerUnit, balanceOperation);
        newOrder.setOrderId(nextOrderId);
        if (orderMap == null) // TODO: Restore from DB
            initOrderMap(); // TODO: What if false?
        orderMap.put(newOrder.getOrderId(), newOrder);
        return newOrder.getOrderId(); // TODO: Return -1 if product does not exist @Giovanni
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
            throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
            UnauthorizedException {
        double totalCost = quantity * pricePerUnit;

        Integer orderId = issueOrder(productCode, quantity, pricePerUnit);
        if (orderId.intValue() < 0)
            return orderId.intValue();

        if (!recordBalanceUpdate(-1 * pricePerUnit * quantity))
            return Integer.valueOf(-1);

        try {
            if (!payOrder(orderId))
                return Integer.valueOf(-1);
        } catch (InvalidOrderIdException e) {
            return Integer.valueOf(-1); // Can never happen...
        }

        Integer balanceId = orderMap.get(orderId).getBalanceId();
        BalanceOperation balanceOperation = new EZBalanceOperation("DEBIT", totalCost);
        accountBook.put(balanceId, balanceOperation);
        return orderId;
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        if (orderId == null)
            throw new InvalidOrderIdException("Null Order ID");
        if (orderId <= 0)
            throw new InvalidOrderIdException("Negative (or zero) Order ID");
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        Order myOrder = orderMap.get(orderId);

        if (myOrder == null)
            return false;
        if (!myOrder.getStatus().matches("(ORDERED|ISSUED)"))
            return false;

        Order o = orderMap.get(orderId);
        double orderPrice = o.getPricePerUnit() * o.getQuantity() * -1;

        if (!recordBalanceUpdate(orderPrice))
            return false;

        myOrder.setStatus("PAYED"); // Sarebbe Paid...
        return true;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId)
            throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        if (orderId == null)
            throw new InvalidOrderIdException("Null Order ID");
        if (orderId <= 0)
            throw new InvalidOrderIdException("Negative (or zero) Order ID");
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
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
        String re = "(ISSUED|ORDERED|COMPLETED)";
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");
        return orderMap.values().stream().filter(o -> o.getStatus().matches(re)).collect(Collectors.toList());
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
        return null;
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {
        return false;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
            UnauthorizedException {
        return false;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
            throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
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


    private boolean initAccountBook() {
        //TODO: Load From DB...
        shopBalance = 0.0;
        accountBook = new HashMap<Integer, BalanceOperation>();
        return true;
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        if (shopBalance - toBeAdded < 0)
            return false;
        shopBalance -= toBeAdded;
        return true;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        if (from.isAfter(to))
            return getCreditsAndDebits(to, from);

        if (accountBook == null)
            initAccountBook(); //TODO: What if null?

        return accountBook
            .values()
            .stream()
            .filter( bo -> from == null ? 
                true : ( bo.getDate().isAfter(from)  || bo.getDate().isEqual(from)))
            .filter( bo -> to == null ?
                true : (bo.getDate().isBefore(to) || bo.getDate().isEqual(to)))
            .collect(Collectors.toList());
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        if (user == null)
            throw new UnauthorizedException("No User Logged In");
        if (!user.getRole().matches("(Administrator|ShopManager)"))
            throw new UnauthorizedException("User has not enough rights");

        shopBalance = accountBook
            .values()
            .stream()
            .mapToDouble( bo -> bo.getMoney() * (bo.getType() == "DEBIT" ? -1 : 1) )
            .sum();
            
        return shopBalance;
    }
}
