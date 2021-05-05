package it.polito.ezshop.data;

public class EZOrder implements Order {

    private static int progressiveOrderId = 0;
    private static boolean progressiveOrderIdSet = false;
    private Integer balanceId;
    private Integer orderId;
    private Integer quantity;
    private String productCode;
    private String status;
    private Double pricePerUnit;


    private static void readProgressiveOrderId() {
        // Call the DB, SELECT MAX(OrderID) FROM ...
        if (!progressiveOrderIdSet)
            progressiveOrderId = 1;
    }

    private static int getProgressiveOrderId(){
        return ++progressiveOrderId;
    }

    public EZOrder(String productCode, int quantity, double pricePerUnit) {
        EZOrder.readProgressiveOrderId();
        this.orderId = Integer.valueOf(getProgressiveOrderId());
        this.quantity = Integer.valueOf(quantity);
        this.pricePerUnit = Double.valueOf(pricePerUnit);
        this.status = "Created";
        this.productCode = productCode;
        this.balanceId = 0; //TODO: FIX THIS
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
