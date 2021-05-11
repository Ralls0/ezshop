package it.polito.ezshop.data;

import java.util.ArrayList;
import java.util.List;

public class EZReturnTransaction {
    private Integer returnId;
    private boolean isCommit;
    private Integer transactionId;
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<EZTicketEntry> products;

    public EZReturnTransaction(Integer transactionId, Integer returnId) {
        this.transactionId = transactionId;
        this.returnId = returnId;
        this.products = new ArrayList<EZTicketEntry>();
        this.status = "open";
    }

    public List<EZTicketEntry> getProducts() {
        return this.products;
    }

    public void setProducts(List<EZTicketEntry> products) {
        this.products = products;
    }

    public boolean isCommit() {
        return isCommit;
    }

    public void setCommit(boolean commit) {
        isCommit = commit;
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
        this.products.add(entry);
        return true;
    }
}
