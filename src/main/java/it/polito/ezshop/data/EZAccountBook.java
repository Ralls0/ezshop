package it.polito.ezshop.data;
import java.util.LinkedList;
import java.util.List;

public class EZAccountBook {
    private Double currentBalance;
    private static EZAccountBook accountBook = null;

    private EZAccountBook() {
        currentBalance = 0.0;
        accountBook = this;
    }

    public static EZAccountBook getInstance() {
        if (accountBook == null)
            accountBook = new EZAccountBook();
        accountBook.computeBalance();
        return accountBook;
    }

    public List<BalanceOperation> getBalanceOperationsList() {
        List<BalanceOperation> operations = null;
        try {
            operations = EZShopDBManager.getInstance().loadAllBalanceOperations();
        } catch (Exception dbException) {
            dbException.printStackTrace();
            operations =  new LinkedList<BalanceOperation>();
        }

        return operations;
    }

    public void computeBalance() {
        List<BalanceOperation> operations = accountBook.getBalanceOperationsList();

        currentBalance = operations.stream().mapToDouble(bo -> bo.getMoney() * (bo.getType().matches("DEBIT") ? -1 : 1)).sum();
    }

    public double getBalance() {
        return currentBalance;
    }

    public boolean recordBalance(Double money) {
        if (currentBalance + money < 0.0)
            return false;
        
        Integer nextBalaceOperationID = null;
        EZBalanceOperation balanceOperation = null;

        try {
            nextBalaceOperationID = EZShopDBManager.getInstance().getNextBalanceOperationID();
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        currentBalance += money;
        balanceOperation = new EZBalanceOperation(money);
        balanceOperation.setBalanceId(nextBalaceOperationID);

        try {
            EZShopDBManager.getInstance().saveBalanceOperation(balanceOperation);
        } catch (Exception dbException) {
            dbException.printStackTrace();
            return false;
        }

        return true;
    }

}
