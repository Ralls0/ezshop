package it.polito.ezshop.data;

import java.util.HashMap;
import java.util.Map;

public class EZAccountBook {
    private HashMap<Integer, BalanceOperation> balanceOperations;
    private Integer currentBalanceOperationID;
    private Double currentBalance;
    private static EZAccountBook accountBook = null;

    private EZAccountBook() {
        balanceOperations = new HashMap<Integer, BalanceOperation>();
        currentBalanceOperationID = 1;
        currentBalance = 0.0;
        // TODO: Load from DB
        accountBook = this;
    }

    public static EZAccountBook loadAccountBook() {
        if (accountBook == null)
            accountBook = new EZAccountBook();
        accountBook.computeBalance();
        return accountBook;
    }

    public void computeBalance() {
        currentBalance = balanceOperations
                            .values()
                            .stream()
                            .mapToDouble(bo -> bo.getMoney() * (bo.getType().matches("DEBIT") ? -1 : 1))
                            .sum();
    }

    public double getBalance() {
        return currentBalance;
    }

    public Integer getCurrentBalanceOperationID() {
        return currentBalanceOperationID;
    }

    public void setCurrentBalanceOperationID(Integer currentID) {
        currentBalanceOperationID = currentID == null ? -1 : currentID;
    }

    public boolean recordBalance(Double amount) {
        if (currentBalance + amount < 0.0)
            return false;

        currentBalanceOperationID = 1; // TODO: Get from DB, return false on failure
        
        currentBalance += amount;
        String type = amount > 0 ? "CREDIT" : "DEBIT";
        amount = amount > 0 ? amount : amount * -1;

        EZBalanceOperation operation = new EZBalanceOperation(type, amount);
        balanceOperations.put(currentBalanceOperationID, operation);
        operation.setBalanceId(currentBalanceOperationID);
        return true;
    }

    public Map<Integer, BalanceOperation> getAccountBookEntries() {
        return balanceOperations;
    }
    
}
