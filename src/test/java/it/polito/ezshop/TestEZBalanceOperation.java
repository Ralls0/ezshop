package it.polito.ezshop;

import org.junit.Test;

import it.polito.ezshop.data.EZBalanceOperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;

public class TestEZBalanceOperation {

    EZBalanceOperation bo;

    @Before
    public void setUp() {
        bo = new EZBalanceOperation(0, LocalDate.now(), 0.0, "");
    }

    @After
    public void cleanUp() {
        bo = null;
    }

    @Test
    public void testSetBalanceId() {
        Integer balanceId = 42;
        bo.setBalanceId(balanceId);
        assertEquals(balanceId.intValue(), bo.getBalanceId());
    }

    @Test
    public void testSetDate() {
        LocalDate now = LocalDate.now();
        bo.setDate(now);
        assertTrue("Date not matching", now.equals(bo.getDate()));
    }

    @Test
    public void testSetMoney(){
        Double money1 = 420.69, money2 = -420.69, money3 = 0.0;
        String type1 = "CREDIT", type2 = "DEBIT", type3 = "CREDIT";

        bo.setMoney(money1);
        assertEquals(money1, bo.getMoney(), 0.001);
        assertTrue("Type mismatch", bo.getType().equals(type1));

        bo.setMoney(money2);
        assertEquals(-1 * money2, bo.getMoney(), 0.001);
        assertTrue("Type mismatch", bo.getType().equals(type2));

        bo.setMoney(money3);
        assertEquals(money3, bo.getMoney(), 0.001);
        assertTrue("Type mismatch", bo.getType().equals(type3));
    }

    @Test
    public void testSetType(){
        String type = "TYPE";
        bo.setType(type);
        assertTrue("Type mismatch", bo.getType().equals(type));
    }

}
