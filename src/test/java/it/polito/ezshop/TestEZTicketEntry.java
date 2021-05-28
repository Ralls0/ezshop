package it.polito.ezshop;

import it.polito.ezshop.data.EZTicketEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEZTicketEntry {

    EZTicketEntry ticket;

    @Before
    public void setUp() {
        ticket = new EZTicketEntry("0000000000000", "Test", 0, 0.0, 0.0);
    }

    @After
    public void tearDown() {
        ticket = null;
    }

    @Test
    public void testSetBarCode() {
        String barCode = "9999999999999";
        ticket.setBarCode(barCode);
        assertEquals(barCode, ticket.getBarCode());
    }

    @Test
    public void testSetProductDescription() {
        String description = "Test descrizione";
        ticket.setProductDescription(description);
        assertEquals(description, ticket.getProductDescription());
    }

    @Test
    public void testSetAmount() {
        Integer amount = 10;
        ticket.setAmount(amount);
        assertEquals(amount.intValue(), ticket.getAmount());
    }

    @Test
    public void testSetPricePerUnit() {
        Double price = 10.25;
        ticket.setPricePerUnit(price);
        assertEquals(price, ticket.getPricePerUnit(), 0.0);
    }

    @Test
    public void testSetDiscountRate() {
        Double discount = 10.25;
        ticket.setDiscountRate(discount);
        assertEquals(discount, ticket.getDiscountRate(), 0.0);
    }
}