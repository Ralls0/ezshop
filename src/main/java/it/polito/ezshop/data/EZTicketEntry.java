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
        return this.productCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.productCode = barCode;
    }

    @Override
    public String getProductDescription() {
        return this.productDescription;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public int getAmount() {
        return this.quantity;
    }

    @Override
    public void setAmount(int amount) {
        this.quantity = amount;
    }

    @Override
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public int hashCode(){
        return productCode.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof EZTicketEntry))
            return false;
        EZTicketEntry otherTicket = (EZTicketEntry) other;

        return this.productCode.equals(otherTicket.getBarCode());
    }
}
