package it.polito.ezshop.data;

import java.util.List; 
import java.util.ArrayList;

public class EZSaleTransaction implements SaleTransaction {

    private Integer id;
    private List<TicketEntry> products;
    private String paymentType;
    private String status;
    private double discountRate;
    private double price;

    public EZSaleTransaction (Integer id) {
            this.id = id;
            this.products = new ArrayList<TicketEntry>();
            this.status = "open";
            this.price = -1.0;

            //  this.price = 0d;
            this.discountRate = 0d;
        }

    public List<TicketEntry> getProducts() {
		return this.products;
	}

    public void setProducts(List<TicketEntry> products)
    {
		this.products = products;
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
        return this.id;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        this.id = ticketNumber;
    }

    @Override
    public List<TicketEntry> getEntries() {
        return this.products;
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {
        this.products = entries;
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
        if (this.price != -1.0) {
            return this.price-(this.price*this.discountRate);
        }
        else {
            double sum = 0.0;
            for(TicketEntry p : products) {
                sum += (p.getPricePerUnit()*p.getAmount())-(p.getDiscountRate()*p.getPricePerUnit()*p.getAmount()); 
            }
            return sum-(sum*this.discountRate);
        }
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public Integer computePoints() {
        if (this.price != -1.0) {
            return Integer.valueOf((int)(this.price / 10));
        }
        else {
            return Integer.valueOf((int)(this.getPrice() / 10));
        }
    }

    public double receiveCashPayment(double cash) {
        double returnCash;
        if (this.price != -1.0) {
            returnCash = cash - this.price;
        }
        else {
            returnCash = cash - this.getPrice();
        }

        if(returnCash < 0) return -1;

        this.status = "payed";
        return returnCash;
    }

    public boolean receiveCreditCardPayment(String circuit) {
        this.status = "payed";
        return true;
    }

    public TicketEntry getEntry(String productCode) {

        for(TicketEntry p : products) {
            if(productCode.equals(p.getBarCode()))  return p;
        }
        return null;
    }

    public boolean addProductToSale(String productCode, String productDescription, Double pricePerUnit, Double discountRate, int amount) {

        for(TicketEntry p : products) {
            if(productCode.equals(p.getBarCode())) {
                p.setAmount(p.getAmount()+Integer.valueOf(amount));
                return true;
            }
        }
        try {
            this.products.add(
                new EZTicketEntry(
                    productCode,
                    productDescription,
                    Integer.valueOf(amount),
                    pricePerUnit,
                    discountRate
                )
            );
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }

        return true;
    }

    public boolean deleteProductFromSale(String productCode, int amount) {

        for(TicketEntry p : products) {
            if(productCode.equals(p.getBarCode())) {
                if(amount > p.getAmount()) {
                    return false;
                }
                if(amount < p.getAmount()){
                    p.setAmount(p.getAmount()-amount);
                }
                else { 
                    products.remove(p);
                }
                return true;
            }
        }

        return false;
    }

    public boolean applyDiscountRateToProduct(String productCode, double discountRate) {
        
        for(TicketEntry p : products) {
            if(productCode.equals(p.getBarCode())) {
                p.setDiscountRate(discountRate);
                return true;
            }
        }

        return false;
    }
    
    public boolean endSaleTransaction() {
        if (this.status.equals("closed")) {
            return false;
        }
        else {
            this.status = "closed";
            return true;
        }
    }

    public static boolean validLuhnAlgorithm(String cardNo) {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        
        for (int i = nDigits - 1; i >= 0; i--)
        {
    
            int d = cardNo.charAt(i) - '0';
    
            if (isSecond == true)
                d = d * 2;
    
            nSum += d / 10;
            nSum += d % 10;
    
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public int hashCode(){
        return id.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof EZSaleTransaction))
            return false;
        EZSaleTransaction otherSale = (EZSaleTransaction) other;

        return this.id == otherSale.getTicketNumber();
    }
    
}
