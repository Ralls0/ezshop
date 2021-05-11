package it.polito.ezshop.data;

public class EZReturnTransaction {
    private Integer saleNumber;
    private Integer id;
    private Integer returnId;
    private boolean isCommit;
    private Integer transactionId;

    public EZReturnTransaction(Integer saleNumber, Integer id) {
        this.saleNumber = saleNumber;
        this.id = id;
    }

    public boolean isCommit() {
        return isCommit;
    }

    public void setCommit(boolean commit) {
        isCommit = commit;
    }



    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }


    public boolean addProductReturned(EZTicketEntry entry) {
        return true;
    }
}
