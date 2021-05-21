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
        // try {
        //     ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
        //     ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @After
    public void tearDown() {
        ezShop.reset();
        ezShop = null;
    }

    @Test
    public void testStartSaleTransaction() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, ()->{ezShop.startSaleTransaction();});

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
        
        try {
            ezShop.createProductType("Product test", productCode, 12.0, "None");
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
        assertTrue("StartSaleTransaction mismatch", transactionId >= 0);

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, ()->{ezShop.addProductToSale(tempId, productCode, 12);});

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException 
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, ()->{ezShop.addProductToSale(null, productCode, 12);});
        
        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, ()->{ezShop.addProductToSale(tempId, null, 12);});

        // test transactionId not valid
        boolean resultAddProductToSale = true;
        try {
            resultAddProductToSale = ezShop.addProductToSale(10, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("AddProductToSale mismatch", !resultAddProductToSale);
        
        // test transactionId valid
        resultAddProductToSale = false;
        try {
            resultAddProductToSale = ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("AddProductToSale mismatch", !resultAddProductToSale);
    }
}