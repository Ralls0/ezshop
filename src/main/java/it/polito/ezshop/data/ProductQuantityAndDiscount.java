package it.polito.ezshop.data;

public class ProductQuantityAndDiscount {
    
    private Integer quantity;
    private double discountRate;
    ProductType product;

    public ProductQuantityAndDiscount (
        Integer quantity,
        double discountRate,
        ProductType product
    ) {
        this.quantity = quantity;
        this.discountRate = discountRate;
        this.product = product;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getDiscountRate() {
        return this.discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public ProductType getProduct() {
        return this.product;
    }

    public void setProduct(ProductType product) {
        this.product = product;
    }

}
