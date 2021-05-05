package it.polito.ezshop.data;

public class EZOrder implements Order {

    private Integer balanceId;
    private Integer orderId;
    private Integer quantity;
    private String productCode;
    private String status;
    private Double pricePerUnit;

    public EZOrder(String productCode, int quantity, double pricePerUnit) {
        this.orderId = Integer.valueOf(-1);
        this.quantity = Integer.valueOf(quantity);
        this.pricePerUnit = Double.valueOf(pricePerUnit);
        this.status = "ISSUED";
        this.productCode = productCode;
        this.balanceId = 0; // TODO: FIX THIS
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
        return pricePerUnit.doubleValue();
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = Double.valueOf(pricePerUnit);

    }

    @Override
    public int getQuantity() {
        return quantity.intValue();
    }

    @Override
    public void setQuantity(int quantity) {
        quantity = Integer.valueOf(quantity);
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
