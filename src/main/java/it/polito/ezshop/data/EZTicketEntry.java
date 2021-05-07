package it.polito.ezshop.data;

public class EZTicketEntry implements TicketEntry{

    private String productCode;
    private String productDescription;
    private Integer quantity;
    private Double pricePerUnit;
    private Double discountRate;

    public EZTicketEntry(String productCode, String productDescription, Integer quantity, Double pricePerUnit, Double discountRate) {
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.discountRate = discountRate;
    }

    @Override
    public String getBarCode() {
        return null;
    }

    @Override
    public void setBarCode(String barCode) {

    }

    @Override
    public String getProductDescription() {
        return null;
    }

    @Override
    public void setProductDescription(String productDescription) {

    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public void setAmount(int amount) {

    }

    @Override
    public double getPricePerUnit() {
        return 0;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {

    }

    @Override
    public double getDiscountRate() {
        return 0;
    }

    @Override
    public void setDiscountRate(double discountRate) {

    }
}
