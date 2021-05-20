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
    public void setReturnId() {
        Integer returnId = 9999999;
        returnTransaction.setReturnId(returnId);
        assertEquals(returnId, returnTransaction.getReturnId());
    }

    @Test
    public void setTransactionId() {
        Integer transactionId = 9999999;
        returnTransaction.setTransactionId(transactionId);
        assertEquals(transactionId, returnTransaction.getTransactionId());
    }

    @Test
    public void setProducts() {
        List<EZTicketEntry>  transactionId = new ArrayList<EZTicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        transactionId.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        transactionId.add(ticketEntry);

        returnTransaction.setProducts(transactionId);
        assertEquals(transactionId, returnTransaction.getProducts());
    }

    @Test
    public void setCommit() {
        boolean commit = true;
        returnTransaction.setCommit(commit);
        assertTrue("Commit mismatch", returnTransaction.isCommit());
    }

    @Test
    public void setStatus() {
        String status = "open";
        returnTransaction.setStatus(status);
        assertTrue("Status mismatch", returnTransaction.getStatus().equals("open"));
    }

    @Test
    public void setDiscountRate() {
        double discountRate = 0.1;
        returnTransaction.setDiscountRate(discountRate);
        assertEquals(discountRate, returnTransaction.getDiscountRate(), 0.01);
    }

    @Test
    public void addProductReturnedAndGetPrice() {
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        returnTransaction.addProductReturned(ticketEntry);
        assertEquals(48.0, returnTransaction.getPrice(), 0.01);
    }

    @Test
    public void addProductReturnedAndGetPriceWithDiscount() {
        double discountRate = 0.1;
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        returnTransaction.addProductReturned(ticketEntry);
        returnTransaction.setDiscountRate(discountRate);
        assertEquals(43.2, returnTransaction.getPrice(), 0.01);
    }

}