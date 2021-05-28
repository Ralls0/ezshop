package it.polito.ezshop;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.EZAccountBook;
import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShopDBManager;

public class TestEZAccountBook {

    private EZAccountBook ab;

    private void resetDB() {
        try {
            EZShopDBManager.getInstance().createTableIfNotExists();
            EZShopDBManager.getInstance().resetDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {
        resetDB();
        ab = EZAccountBook.getInstance();
    }

    @Test
    public void testSingleton() {
        EZAccountBook i = EZAccountBook.getInstance();
        assert (i == ab);
    }

    @Test
    public void testGetBOList() {
        List<BalanceOperation> l;

        ab.recordBalance(200.0);
        ab.recordBalance(100.0);
        ab.recordBalance(-50.0);
        ab.recordBalance(-50.0);

        long tot, ndebits, ncredits;

        l = ab.getBalanceOperationsList();
        tot = l.stream().count();
        assertEquals(4, tot);

        ndebits = l.stream().filter(bo -> bo.getType().matches("DEBIT")).count();
        ncredits = l.stream().filter(bo -> bo.getType().matches("CREDIT")).count();
        assertEquals(2, ncredits);
        assertEquals(2, ndebits);

        long count;
        count = l.stream().filter(bo -> bo.getMoney() == 50.0).count();
        assertEquals(2, count);
        count = l.stream().filter(bo -> bo.getMoney() == 200.0).count();
        assertEquals(1, count);
        count = l.stream().filter(bo -> bo.getMoney() == 100.0).count();
        assertEquals(1, count);
    }

    @Test
    public void testComputeBalanceAllPositive() {
        double sum;

        ab.recordBalance(200.0);
        ab.recordBalance(400.0);
        ab.recordBalance(600.0);
        sum = ab.getBalance();
        assertEquals(1200, sum, 0.01);
    }

    @Test
    public void testComputeBalanceAllNegative() {
        double sum;

        ab.recordBalance(-200.0);
        ab.recordBalance(-400.0);
        ab.recordBalance(-600.0);
        sum = ab.getBalance();
        assertEquals(0, sum, 0.01);
    }

    @Test
    public void testComputeBalanceMixed1() {
        double sum;
        resetDB();

        ab.recordBalance(600.0);
        ab.recordBalance(-200.0);
        ab.recordBalance(-400.0);
        sum = ab.getBalance();
        assertEquals(0.00, sum, 0.01);
    }

    @Test
    public void testComputeBalanceMixed2() {
        double sum;

        ab.recordBalance(200.0);
        ab.recordBalance(-400.0);
        ab.recordBalance(600.0);
        sum = ab.getBalance();
        assertEquals(800.0, sum, 0.01);
    }

    @Test
    public void testComputeBalanceMixed3() {
        double sum;

        ab.recordBalance(600.0);
        ab.recordBalance(-500.0);
        ab.recordBalance(400.0);
        sum = ab.getBalance();
        assertEquals(500.0, sum, 0.01);
    }
}
