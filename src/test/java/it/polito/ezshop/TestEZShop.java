package it.polito.ezshop;

import it.polito.ezshop.data.EZShopInterface;
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
    }

    @After
    public void tearDown() {
        ezShop.reset();
        ezShop = null;
    }

    @Test
    public void testCreateUser() {

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.createUser("",
                    "password", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.createUser(null,
                    "password", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni",
                    "", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni",
                    null, "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.createUser("Giovanni",
                    "password", "Cashier");
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



    }

    @Test
    public void testGetAllUsers() {
    }

    @Test
    public void testGetUser() {
    }

    @Test
    public void testUpdateUserRights() {
    }

    @Test
    public void testLogin() {
    }

    @Test
    public void testLogout() {
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

}

