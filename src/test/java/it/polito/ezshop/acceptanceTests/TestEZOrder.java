package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.EZOrder;

public class TestEZOrder {
    
    EZOrder order;

    @Before
    public void setUp() {
        order = new EZOrder(0, 0, 0, "", "", 0.0);
    }

    @After
    public void cleanUp() {
        order = null;
    }

    @Test
    public void testSetBalanceId(){
        Integer balanceID = 42;
        order.setBalanceId(balanceID);
        assertEquals(balanceID, order.getBalanceId());
    }

    @Test
    public void testSetProductCode(){
        String productCode = "productCode";
        order.setProductCode(productCode);
        assertTrue("ProductCode Mismatch", order.getProductCode().equals(productCode));
    }

    @Test
    public void testSetPricePerUnit(){
        Double pricePerUnit = 420.69;
        order.setPricePerUnit(pricePerUnit);
        assertEquals(pricePerUnit.doubleValue(), order.getPricePerUnit(), 0.001);
    }

    @Test
    public void testSetQuantity() {
        Integer qty = 42;
        order.setQuantity(qty);
        assertEquals(qty.intValue(), order.getQuantity());
    }

    @Test
    public void testSetStatus() {
        String status = "status";
        order.setStatus(status);
        assertTrue("Status mismatch", order.getStatus().equals(status));
    }

    
    @Test
    public void testSetOrderID(){
        Integer orderID = 42;
        order.setOrderId(orderID);
        assertEquals(orderID, order.getOrderId());
    }
}
