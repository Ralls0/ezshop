package it.polito.ezshop.acceptanceTests;

import it.polito.ezshop.data.EZProductType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEZProductType {

    EZProductType product;
    @Before
    public void setUp() {
        product = new EZProductType(0, 0, "0000000000000", "Test", "Test", "", 0.0);
    }

    @After
    public void tearDown() {
        product = null;
    }

    @Test
    public void setQuantity() {
        Integer quantity = 10;
        product.setQuantity(quantity);
        assertEquals(quantity, product.getQuantity());
    }

    @Test
    public void setLocation() {
        String location = "1-a-1";
        product.setLocation(location);
        assertEquals(location, product.getLocation());
    }

    @Test
    public void setNote() {
        String note = "Prova";
        product.setNote(note);
        assertEquals(note, product.getNote());
    }

    @Test
    public void setProductDescription() {
        String description = "Prova descrizione";
        product.setProductDescription(description);
        assertEquals(description, product.getProductDescription());
    }

    @Test
    public void setBarCode() {
        String barCode = "9999999999999";
        product.setBarCode(barCode);
        assertEquals(barCode, product.getBarCode());
    }

    @Test
    public void setPricePerUnit() {
        Double price = 10.25;
        product.setPricePerUnit(price);
        assertEquals(price, product.getPricePerUnit());

    }

    @Test
    public void setId() {
        Integer id = 22;
        product.setId(id);
        assertEquals(id, product.getId());
    }
}