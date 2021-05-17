package it.polito.ezshop;

import it.polito.ezshop.data.CreditCardCircuit;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestCreditCardCircuit {

    //  isPresent
    @Test
    public void testCardPresentInFile() throws FileNotFoundException {
        boolean result = CreditCardCircuit.getInstance().isCardPresent("4992739871645621");
        Assert.assertTrue(result);
    }

    @Test
    public void testCardNotPresentInFile() throws FileNotFoundException {
        boolean result = CreditCardCircuit.getInstance().isCardPresent("1992739871645621");
        Assert.assertFalse(result);
    }

    //  hasEnoughBalance
    @Test
    public void testNegativeMinBalance() throws FileNotFoundException {
        boolean result = CreditCardCircuit.getInstance().hasEnoughBalance("4992739871645621", -1d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardNotPresentDuringHasEnoughBalance() throws FileNotFoundException {
        boolean result = CreditCardCircuit.getInstance().hasEnoughBalance("1992739871645621", 5d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardBalanceInsufficientDuringHasEnoughBalance() throws FileNotFoundException {
        boolean result = CreditCardCircuit.getInstance().hasEnoughBalance("4992739871645621", 100d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardBalanceSufficientDuringHasEnoughBalance() throws FileNotFoundException {
        boolean result = CreditCardCircuit.getInstance().hasEnoughBalance("4992739871645621", 5d);
        Assert.assertTrue(result);
    }

    //  pay
    @Test
    public void testNegativePayAmount() throws IOException {
        boolean result = CreditCardCircuit.getInstance().pay("4992739871645621", -1d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardNotPresentDuringPay() throws IOException {
        boolean result = CreditCardCircuit.getInstance().pay("1992739871645621", 5d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardBalanceInsufficientDuringPay() throws IOException {
        boolean result = CreditCardCircuit.getInstance().pay("4992739871645621", 100d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardBalanceSufficientDuringPay() throws IOException {
        boolean result = CreditCardCircuit.getInstance().pay("4992739871645621", 5d);
        Assert.assertTrue(result);
    }

    //  refund
    @Test
    public void testNegativeRefundAmount() throws IOException {
        boolean result = CreditCardCircuit.getInstance().refund("4992739871645621", -10d);
        Assert.assertFalse(result);
    }

    @Test
    public void testCardNotPresentDuringRefund() throws IOException {
        boolean result = CreditCardCircuit.getInstance().refund("1992739871645621", 10d);
        Assert.assertFalse(result);
    }

    @Test
    public void testRefundSuccess() throws IOException {
        boolean result = CreditCardCircuit.getInstance().refund("4992739871645621", 5d);
        Assert.assertTrue(result);
    }
}
