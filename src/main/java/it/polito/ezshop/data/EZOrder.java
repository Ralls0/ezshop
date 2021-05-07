package it.polito.ezshop.data;

public class EZOrder implements Order{
    Integer orderID;
    Integer balanceID;
    String productCode;
    String status;
    Double pricePerUnit;
    Integer quantity;

    public EZOrder(Integer orderID, Integer balanceID, String productCode, String status, Double pricePerUnit, Integer quantity) {
        this.orderID = orderID;
        this.balanceID = balanceID;
        this.productCode = productCode;
        this.status = status;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    @Override
    public Integer getBalanceId() {
        return null;
    }

    @Override
    public void setBalanceId(Integer balanceId) {

    }

    @Override
    public String getProductCode() {
        return null;
    }

    @Override
    public void setProductCode(String productCode) {

    }

    @Override
    public double getPricePerUnit() {
        return 0;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {

    }

    @Override
    public int getQuantity() {
        return 0;
    }

    @Override
    public void setQuantity(int quantity) {

    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public void setStatus(String status) {

    }

    @Override
    public Integer getOrderId() {
        return null;
    }

    @Override
    public void setOrderId(Integer orderId) {

    }
}
