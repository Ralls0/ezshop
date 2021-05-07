package it.polito.ezshop.data;

public class EZOrder implements Order {

    private Integer orderId;
    private Integer balanceId;
    private Integer quantity;
    private String productCode;
    private String status;
    private Double pricePerUnit;


    public EZOrder(String productCode, int quantity, double pricePerUnit) {
        this.orderId = -1;
        this.balanceId = -1;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.status = "ISSUED";
        this.productCode = productCode;
    }

    public EZOrder(Integer orderId, Integer balanceId, Integer quantity, String productCode, String status, Double pricePerUnit) {
        this.orderId = orderId;
        this.balanceId = balanceId;
        this.quantity = quantity;
        this.productCode = productCode;
        this.status = status;
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public Integer getBalanceId() {
        return balanceId;
    }

    @Override
    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    @Override
    public String getProductCode() {
        return productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        this.productCode = productCode;

    }

    @Override
    public double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;

    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;

    }

    @Override
    public Integer getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
