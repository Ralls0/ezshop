package it.polito.ezshop;

import it.polito.ezshop.data.EZReturnTransaction;
import it.polito.ezshop.data.EZTicketEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestEZReturnTransaction {

    EZReturnTransaction returnTransaction;

    @Before
    public void setUp() {
        returnTransaction = new EZReturnTransaction(0, 0); 
    }

    @After
    public void tearDown() {
        returnTransaction = null;
    }

    @Test
    public void testSetReturnId() {
        Integer returnId = 9999999;
        returnTransaction.setReturnId(returnId);
        assertEquals(returnId, returnTransaction.getReturnId());
    }

    @Test
    public void testSetTransactionId() {
        Integer transactionId = 9999999;
        returnTransaction.setTransactionId(transactionId);
        assertEquals(transactionId, returnTransaction.getTransactionId());
    }

    @Test
    public void testSetProducts() {
        List<EZTicketEntry>  ticketEntries = new ArrayList<EZTicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        returnTransaction.setProducts(ticketEntries);
        assertEquals(ticketEntries, returnTransaction.getProducts());
    }

    @Test
    public void testSetCommit() {
        boolean commit = true;
        returnTransaction.setCommit(commit);
        assertTrue("Commit mismatch", returnTransaction.isCommit());
    }

    @Test
    public void testSetStatus() {
        String status = "open";
        returnTransaction.setStatus(status);
        assertTrue("Status mismatch", returnTransaction.getStatus().equals("open"));
    }

    @Test
    public void testSetDiscountRate() {
        double discountRate = 0.1;
        returnTransaction.setDiscountRate(discountRate);
        assertEquals(discountRate, returnTransaction.getDiscountRate(), 0.01);
    }

    @Test
    public void testAddProductReturnedAndGetPrice() {
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        returnTransaction.addProductReturned(ticketEntry);
        assertEquals(48.0, returnTransaction.getPrice(), 0.01);
    }

    @Test
    public void testAddProductReturnedAndGetPriceWithDiscount() {
        double discountRate = 0.1;
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        returnTransaction.addProductReturned(ticketEntry);
        returnTransaction.setDiscountRate(discountRate);
        assertEquals(43.2, returnTransaction.getPrice(), 0.01);
    }

}