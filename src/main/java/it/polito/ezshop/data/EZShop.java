package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EZShop implements EZShopInterface {

    // Users variable
    private List<User> users = new ArrayList<>();
    private User authenticatedUser;

    // Products variable
    private List<ProductType> products = new ArrayList<>();

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

        EZUser user = new EZUser(i, username, password, role);
        i++;

        // Solve the duplicate problem
        if (authenticatedUser == null) {
            users.add(user);
        }

        // select user count from db
        // update the db

        return user.getId();
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
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                authenticatedUser = user;
            }
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

        if(productId <= 0)
            throw new InvalidProductIdException();

        for (ProductType product : products) {
            if (product.getId().equals(productId)){
                if(!product.getLocation().equals("")){
                    if(product.getQuantity() + toBeAdded < 0)
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

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException,
            InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
            throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
            UnauthorizedException {
        return null;
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId)
            throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        return false;
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        return null;
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

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        return false;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        return null;
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        return 0;
    }
}
