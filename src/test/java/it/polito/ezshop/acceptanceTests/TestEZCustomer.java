package it.polito.ezshop.acceptanceTests;

import org.junit.Test;

import it.polito.ezshop.data.EZCustomer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;

public class TestEZCustomer {

    EZCustomer customer;

    @Before
    public void setUp() {
        customer = new EZCustomer(0, "Test Name", "Test Card", 0);
    }

    @After
    public void cleanUp() {
        customer = null;
    }

    @Test
    public void testXSetter() {
        assertTrue("Fake", true);
    }

    @Test
    public void testCustomerCardSetter() {
        String customerCard = "1234567890";
        customer.setCustomerCard(customerCard);
        assertTrue("Customer Card doesn't match", 
            customer.getCustomerCard().matches(customerCard));
    }

    @Test
    public void testCustomerNameSetter() {
        String customerName = "Mario Rossi";
        customer.setCustomerName(customerName);
        assertTrue("Customer Card doesn't match", 
            customer.getCustomerName().matches(customerName));
    }

    @Test
    public void testSetId(){
        Integer id1 = 42;
        Integer id2 = 0;
        Integer id3 = -42;
        customer.setId(id1);
        assertEquals(id1, customer.getId());
        customer.setId(id2);
        assertEquals(id1, customer.getId());
        customer.setId(id3);
        assertEquals(id1, customer.getId());
    }

    @Test
    public void testSetPoints(){
        Integer points = 42;
        customer.setPoints(points);
        assertEquals(points, customer.getPoints());
    }

}
