package it.polito.ezshop;

import it.polito.ezshop.data.EZOrder;
import it.polito.ezshop.data.EZProductType;
import it.polito.ezshop.data.EZShopDBManager;
import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.data.EZUser;
import it.polito.ezshop.data.Order;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestEZShop {

    EZShopInterface ezShop;

    @Before
    public void setUp() {
        ezShop = new it.polito.ezshop.data.EZShop();
        ezShop.reset();
    }

    @After
    public void tearDown() {
        ezShop.reset();
        ezShop = null;
    }

    @Test
    public void testCreateUser() {

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.createUser("", "password", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.createUser(null, "password", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni", "", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni", null, "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.createUser("Giovanni", "password", "Cashier");
        });

        // ------------------------------------------------------------- //

        Integer id = -1;
        try {
            id = ezShop.createUser("Giovanni", "password", "Administrator");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(id == 1);

        // ------------------------------------------------------------- //

        try {
            id = ezShop.createUser("Giovanni", "password", "Administrator");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(id == -1);

    }

    @Test
    public void testDeleteUser() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteUser(1);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.deleteUser(-1);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.deleteUser(null);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.logout();
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteUser(0);
        });

        // ------------------------------------------------------------- //

        boolean deleted = false;
        try {
            ezShop.logout();
            ezShop.login("Giovanni", "password");
            deleted = ezShop.deleteUser(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(deleted);

    }

    @Test
    public void testGetAllUsers() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getAllUsers();
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.logout();
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getAllUsers();
        });

        // ------------------------------------------------------------- //

        List<User> users = null;

        try {
            ezShop.logout();
            ezShop.login("Giovanni", "password");
            users = ezShop.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(users);

    }

    @Test
    public void testGetUser() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.getUser(-1);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.getUser(null);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getUser(2);
        });

        // ------------------------------------------------------------- //

        User user = null;
        try {
            ezShop.logout();
            ezShop.login("Giovanni", "password");
            user = ezShop.getUser(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(user);

    }

    @Test
    public void testUpdateUserRights() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.updateUserRights(-1, "ShopManager");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.updateUserRights(null, "ShopManager");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.updateUserRights(1, null);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.updateUserRights(1, "Ruolo");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateUserRights(2, null);
        });

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateUserRights(2, "ShopManager");
        });

        // ------------------------------------------------------------- //

        boolean modified = false;
        try {
            ezShop.login("Giovanni", "password");
            modified = ezShop.updateUserRights(2, "ShopManager");
            assertTrue(modified);
            modified = false;
            modified = ezShop.updateUserRights(3, "ShopManager");
            assertFalse(modified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin() {
        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.login("", "password");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.login(null, "password");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.login("Giovanni", "");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni", null, "Administrator");
        });

        // ------------------------------------------------------------- //

        User user = null;

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            user = ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(user);

    }

    @Test
    public void testLogout() {
        boolean logged_out = false;
        logged_out = ezShop.logout();
        assertTrue(logged_out);
    }

    @Test
    public void createProductType() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidPricePerUnitException.class, () -> {
            ezShop.createProductType("descrizione", "3000000000083", -9.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductDescriptionException.class, () -> {
            ezShop.createProductType("", "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductDescriptionException.class, () -> {
            ezShop.createProductType(null, "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.createProductType("descrizione", "", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.createProductType("descrizione", null, 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.createProductType("descrizione", "3000000000087", 10.5, "nota");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
        });

        // ------------------------------------------------------------- //

        Integer productId = -1;
        try {
            ezShop.login("Giovanni", "password");
            productId = ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            assertTrue(productId > 0);
            productId = ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            assertEquals(-1, (int) productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void deleteProductType() {
    }

    @Test
    public void getAllProductTypes() {
    }

    @Test
    public void getProductTypeByBarCode() {
    }

    @Test
    public void getProductTypesByDescription() {
    }

    @Test
    public void updateQuantity() {
    }

    @Test
    public void updatePosition() {
    }

    @Test
    public void defineCustomer() {
    }

    @Test
    public void modifyCustomer() {
    }

    @Test
    public void deleteCustomer() {
    }

    @Test
    public void getCustomer() {
    }

    @Test
    public void getAllCustomers() {
    }

    @Test
    public void createCard() {
    }

    @Test
    public void attachCardToCustomer() {
    }

    @Test
    public void modifyPointsOnCard() {
    }

    @Test
    public void testStartSaleTransaction() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.startSaleTransaction();
        });

        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer id = -1;
        try {
            id = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
        assertTrue("StartSaleTransaction mismatch", id >= 0);
    }

    @Test
    public void testAddProductToSale() {

        // start saleTransaction
        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Product
        String productCode = "3000000000076";
        Integer productId = -1;
        try {
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create SaleTransaction
        Integer transactionId = -1;

        try {
            transactionId = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.addProductToSale(tempId, productCode, 12);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.addProductToSale(null, productCode, 12);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.addProductToSale(tempId, null, 12);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.addProductToSale(10, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("AddProductToSale mismatch", !resultOp);

        // test transactionId valid
        resultOp = false;
        try {
            resultOp = ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("AddProductToSale mismatch", resultOp);
    }

    @Test
    public void testDeleteProductFromSale() {

        // start saleTransaction
        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Product
        String productCode = "3000000000076";
        Integer productId = -1;
        try {
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create SaleTransaction
        Integer transactionId = -1;

        try {
            transactionId = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        try {
            ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteProductFromSale(tempId, productCode, 12);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.deleteProductFromSale(null, productCode, 12);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.deleteProductFromSale(tempId, null, 12);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.deleteProductFromSale(10, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("DeleteProduct mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.deleteProductFromSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("DeleteProduct mismatch", resultOp);
    }

    @Test
    public void testApplyDiscountRateToProduct() {

        // start saleTransaction
        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Product
        String productCode = "3000000000076";
        Integer productId = -1;
        try {
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create SaleTransaction
        Integer transactionId = -1;

        try {
            transactionId = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        try {
            ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, productCode, 0.1);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.applyDiscountRateToProduct(null, productCode, 0.1);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, null, 0.1);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, productCode, -1.0);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, productCode, 1.0);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.applyDiscountRateToProduct(10, productCode, 0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToProduct mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.applyDiscountRateToProduct(tempId, productCode, 0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToProduct mismatch", resultOp);
    }

    @Test
    public void testapplyDiscountRateToSale() {

        // start saleTransaction
        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Product
        String productCode = "3000000000076";
        Integer productId = -1;
        try {
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create SaleTransaction
        Integer transactionId = -1;

        try {
            transactionId = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        try {
            ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.applyDiscountRateToSale(tempId, 0.1);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.applyDiscountRateToSale(null, 0.1);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToSale(tempId, -1.0);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToSale(tempId, 1.0);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.applyDiscountRateToSale(10, 0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToSale mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.applyDiscountRateToSale(tempId, 0.2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToSale mismatch", resultOp);
    }

    @Test
    public void testComputePointsForSale() {

        // start saleTransaction
        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Product
        String productCode = "3000000000076";
        Integer productId = -1;
        try {
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create SaleTransaction
        Integer transactionId = -1;

        try {
            transactionId = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        try {
            ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.computePointsForSale(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.computePointsForSale(null);
        });

        // test transactionId not valid
        int resultOp = 1;
        try {
            resultOp = ezShop.computePointsForSale(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(-1, resultOp);

        resultOp = -1;
        try {
            resultOp = ezShop.computePointsForSale(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(14, resultOp);
    }

    @Test
    public void testEndSaleTransaction() {

        // start saleTransaction
        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Product
        String productCode = "3000000000076";
        Integer productId = -1;
        try {
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create SaleTransaction
        Integer transactionId = -1;

        try {
            transactionId = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        try {
            ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.endSaleTransaction(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.endSaleTransaction(null);
        });

        // test transactionId valid
        boolean resultOp = false;
        try {
            resultOp = ezShop.endSaleTransaction(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("endSaleTransaction mismatch", resultOp);

    }

    private void createw(String username, String passw, String role) {
        try {
            ezShop.createUser(username, passw, role);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    private void loginw(String username, String passw) {
        try {
            ezShop.login(username, passw);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testIssueOrder() {
        String empty = "";
        String nullCode = null;
        String shortCode = "12345";
        String longCode = "123456890123456";
        String alnumCode = "1234567ab0123";
        String invalidCode = "300000000007";
        String validCode = "300000000001";

        Integer negativeQuantity = -10;
        Integer quantity = 10;
        Integer zeroQuantity = 0;
        Integer result;

        Double positivePPU = 420.69;
        Double negativePPU = -positivePPU;
        Double zeroPPU = 0.0;

        Order order;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, quantity, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, quantity, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        /* Product Code */
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(shortCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(longCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(invalidCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(alnumCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(empty, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(nullCode, quantity, positivePPU));

        /* Quantity */
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, negativeQuantity, positivePPU));
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, zeroQuantity, positivePPU));

        /* Price per Unit */
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, quantity, negativePPU));
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, quantity, zeroPPU));

        /* Function Body */

        try {
            result = ezShop.issueOrder(validCode, quantity, positivePPU);
            assertTrue(result == -1); // Product does not exists

            ezShop.createProductType("Descr", validCode, positivePPU, "Integration Test");
            result = ezShop.issueOrder(validCode, quantity, positivePPU);
            assertTrue(result > 0);

            order = EZShopDBManager.getInstance().loadOrder(result);
            assertTrue(order != null);

            assertEquals(order, order.getOrderId());
            assertEquals(positivePPU, order.getPricePerUnit(), 0.001);
            assertEquals(quantity.intValue(), order.getQuantity());
            assertTrue(order.getProductCode().equals(validCode));
            assertTrue(order.getStatus().equals("ISSUED"));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testPayOrderFor() {
        String empty = "";
        String nullCode = null;
        String shortCode = "12345";
        String longCode = "123456890123456";
        String alnumCode = "1234567ab0123";
        String invalidCode = "300000000007";
        String validCode = "300000000001";

        Integer minusOne = -1;
        Integer one = 1;
        Integer zero = 0;
        Integer result;

        Double positivePPU = 420.69;
        Double negativePPU = -positivePPU;
        Double zeroPPU = 0.0;
        Double balanceToAdd = Double.MAX_VALUE;
        Double remainder;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        /* Product Code */
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(shortCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(longCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(invalidCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(alnumCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(empty, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(nullCode, one, positivePPU));

        /* Quantity */
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, minusOne, positivePPU));
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, zero, positivePPU));

        /* Price per Unit */
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, one, negativePPU));
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, one, zeroPPU));

        try {
            result = ezShop.issueOrder(validCode, one, positivePPU);
            assertTrue(result == -1); // Product does not exists

            ezShop.createProductType("Descr", validCode, positivePPU, "Integration Test");
            result = ezShop.issueOrder(validCode, one, positivePPU);
            assertTrue(result > 0);
            ezShop.recordBalanceUpdate(balanceToAdd);
            assertTrue(ezShop.payOrder(result));

            remainder = balanceToAdd - one * positivePPU;
            assertEquals(remainder, ezShop.computeBalance(), 0.01);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void payOrderTest() {

        Integer negativeOrderID = -10;
        Integer validOrderID;
        Integer one = 1;
        Integer zero = 0;

        String validCode = "300000000001";

        Double positivePPU = 420.69;
        Double balanceToAdd = Double.MAX_VALUE;
        Double remainder;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        try {
            ezShop.createProductType("Test Integration", validCode, positivePPU, "Test");
            validOrderID = ezShop.issueOrder(validCode, one, positivePPU);
            ezShop.recordBalanceUpdate(balanceToAdd);
            
            assertThrows(InvalidOrderIdException.class, () -> ezShop.payOrder(negativeOrderID));
            assertThrows(InvalidOrderIdException.class, () -> ezShop.payOrder(zero));
            assertTrue(ezShop.payOrder(validOrderID));

            remainder = balanceToAdd - one * positivePPU;
            assertEquals(remainder, ezShop.computeBalance(), 0.01);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
