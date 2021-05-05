package it.polito.ezshop.data;

import java.util.List;

public class EZSaleTransaction implements SaleTransaction {

    private Integer id;
    // private List<ProductQuantityAndDiscount> products;
    private double cost;
    private String paymentType;
    private String status;
    private double discountRate;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // public private getList<ProductQuantityAndDiscount>()

    // {
	// 	return this.List<ProductQuantityAndDiscount>;
	// }

    // public void setList<ProductQuantityAndDiscount>(private List<ProductQuantityAndDiscount>)
    // {
	// 	this.List<ProductQuantityAndDiscount> = List<ProductQuantityAndDiscount>;
	// }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Integer getTicketNumber() {
        return null;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {

    }

    @Override
    public List<TicketEntry> getEntries() {
        return null;
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {

    }

    @Override
    public double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double getPrice() {
        return 0.0;
    }

    @Override
    public void setPrice(double price) {

    }

}
