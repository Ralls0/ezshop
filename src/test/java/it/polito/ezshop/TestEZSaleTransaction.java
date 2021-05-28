package it.polito.ezshop;

import it.polito.ezshop.data.EZSaleTransaction;
import it.polito.ezshop.data.EZTicketEntry;
import it.polito.ezshop.data.TicketEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestEZSaleTransaction {

    EZSaleTransaction saleTransaction;

    @Before
    public void setUp() {
        saleTransaction = new EZSaleTransaction(0); 
    }

    @After
    public void tearDown() {
        saleTransaction = null;
    }

    @Test
    public void testSetProducts() {
        List<TicketEntry>  ticketEntries = new ArrayList<TicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        saleTransaction.setProducts(ticketEntries);
        assertEquals(ticketEntries, saleTransaction.getProducts());
    }
    
    @Test
    public void testSetEntries() {
        List<TicketEntry>  ticketEntries = new ArrayList<TicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        saleTransaction.setEntries(ticketEntries);
        assertEquals(ticketEntries, saleTransaction.getEntries());
    }

    @Test
    public void testSetPaymentType() {
        String paymentType = "cash";
        saleTransaction.setPaymentType(paymentType);
        assertTrue("Payment type mismatch", saleTransaction.getPaymentType().equals("cash"));
    }

    @Test
    public void testSetStatus() {
        String status = "open";
        saleTransaction.setStatus(status);
        assertTrue("Status mismatch", saleTransaction.getStatus().equals("open"));
    }

    @Test
    public void testSetTicketNumber() {
        Integer transactionId = 9999999;
        saleTransaction.setTicketNumber(transactionId);
        assertEquals(transactionId, saleTransaction.getTicketNumber());
    }

    @Test
    public void testSetDiscountRate() {
        double discountRate = 0.1;
        saleTransaction.setDiscountRate(discountRate);
        assertEquals(discountRate, saleTransaction.getDiscountRate(), 0.01);
    }

    @Test
    public void testSetPrice() {
        double price = 200.50;
        saleTransaction.setPrice(price);
        assertEquals(price, saleTransaction.getPrice(), 0.01);
    }
    
    @Test
    public void testSetPriceWithProduct() {
        saleTransaction.addProductToSale("3000000000076", "productDescription", 12.0, 0.1, 10);
        assertEquals(108.0, saleTransaction.getPrice(), 0.01);
    }

    @Test
    public void testComputePointsWithPrice() {
        double price = 200.50;
        saleTransaction.setPrice(price);
        assertEquals(Integer.valueOf(20), saleTransaction.computePoints());
    }
    
    @Test
    public void testComputePointsWithProduct() {
        saleTransaction.addProductToSale("3000000000076", "productDescription", 12.0, 0.1, 10);
        assertEquals(Integer.valueOf(10), saleTransaction.computePoints());
    }

    @Test
    public void testReceiveCashPaymentValid() {
        double price = 200.50;
        saleTransaction.setPrice(price);
        assertEquals(99.5, saleTransaction.receiveCashPayment(300), 0.01);
        assertTrue("Payment status mismatch", saleTransaction.getStatus().equals("payed"));
    }
    
    @Test
    public void testReceiveCashPaymentInvalid() {
        double price = 200.50;
        saleTransaction.setPrice(price);
        assertEquals(-1.0, saleTransaction.receiveCashPayment(100), 0.01);
        assertTrue("Payment status mismatch", saleTransaction.getStatus().equals("open"));
    }
    
    @Test
    public void testReceiveCreditCardPayment() {
        assertTrue("Credit Card Payment mismatch", saleTransaction.receiveCreditCardPayment("Visa"));
        assertTrue("Payment status mismatch", saleTransaction.getStatus().equals("payed"));
    }

    @Test
    public void testGetEntry() {
        List<TicketEntry>  ticketEntries = new ArrayList<TicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        saleTransaction.setEntries(ticketEntries);
        assertEquals(ticketEntry, saleTransaction.getEntry("3000000000083"));
    }
    
    @Test
    public void testDeleteProductFromSaleValid() {
        List<TicketEntry>  ticketEntries = new ArrayList<TicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        saleTransaction.setEntries(ticketEntries);
        assertTrue("Delete product from sale mismatch", saleTransaction.deleteProductFromSale("3000000000083", 1));
    }
    
    @Test
    public void testDeleteProductFromSaleInvalid() {
        List<TicketEntry>  ticketEntries = new ArrayList<TicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        saleTransaction.setEntries(ticketEntries);
        assertTrue("Delete product from sale mismatch", !saleTransaction.deleteProductFromSale("3000000000083", 2));
    }
    
    @Test
    public void testApplyDiscountRateToProduct() {
        List<TicketEntry>  ticketEntries = new ArrayList<TicketEntry>();
        EZTicketEntry ticketEntry = new EZTicketEntry("3000000000076", "ProductDescription", 4, 12.0, 0.0);
        ticketEntries.add(ticketEntry);
        ticketEntry = new EZTicketEntry("3000000000083", "ProductDescription2", 1, 2.0, 0.1);
        ticketEntries.add(ticketEntry);

        saleTransaction.setEntries(ticketEntries);
        assertTrue("Docount rate to product mismatch", saleTransaction.applyDiscountRateToProduct("3000000000076", 0.2));
        TicketEntry entry = saleTransaction.getEntry("3000000000076");
        assertEquals(0.2, entry.getDiscountRate(), 0.01);
    }

    @Test
    public void testEndSaleTransaction() {
        assertTrue("End sale transaction mismatch", saleTransaction.endSaleTransaction());
        assertTrue("End sale transaction status mismatch", saleTransaction.getStatus().equals("closed"));

    }

    @Test
    public void testValidLuhnAlgorithm() {
        assertTrue("validLuhnAlgorithm mismatch", ! EZSaleTransaction.validLuhnAlgorithm("1231231231"));
        assertTrue("validLuhnAlgorithm mismatch", EZSaleTransaction.validLuhnAlgorithm("4030270951989685"));
    }

}