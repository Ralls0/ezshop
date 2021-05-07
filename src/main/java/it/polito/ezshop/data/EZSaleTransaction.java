package it.polito.ezshop.data;

import java.util.List; 
import java.util.ArrayList; 
import java.util.Iterator; 

public class EZSaleTransaction implements SaleTransaction {

    private Integer id;
    private ArrayList<ProductQuantityAndDiscount> products;
    private String paymentType;
    private String status;
    private double discountRate;
    private double price;

    public EZSaleTransaction () {
            this.id = 1; // TODO: SOSTITUIRE CON QUERY
            this.products = new ArrayList<ProductQuantityAndDiscount>();
            this.status = "open";
            this.price = -1.0;
        }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<ProductQuantityAndDiscount> getProducts() {
		return this.products;
	}

    public void setProducts(ArrayList<ProductQuantityAndDiscount> products)
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
        if (price != -1.0) {
            return this.price;
        }
        else {
            double sum = 0.0;
            for(Iterator<ProductQuantityAndDiscount> i = products.iterator(); i.hasNext();) {
                ProductQuantityAndDiscount p = i.next();
                sum += (p.getProduct().getPricePerUnit()*p.getQuantity())-(p.getDiscountRate()/100*p.getProduct().getPricePerUnit()*p.getQuantity()); 
            }
            return sum-(sum*this.discountRate/100);
        }
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public Integer computePoints() {
        if (price != -1.0) {
            return Integer.valueOf((int)(this.price / 10));
        }
        else {
            return Integer.valueOf((int)(this.getPrice() / 10));
        }
    }

    // TODO: return -1 cash is not enough
    public double receiveCashPayment(double cash) {
        double returnCash;
        if (this.price != -1.0) {
            returnCash = cash - this.price;
        }
        else {
            returnCash = cash - this.getPrice();
        }
        this.status = "payed";
        return returnCash;
    }

    // public boolean receiveCreditCardPayment(CreditCardCircuit circuit) {
    //     return false;
    // }

    // TODO: diminuisci la quantità su scaffali. ProductType quantity?
    public boolean addProductToSale(EZProductType product, int amount) {
        
            for(Iterator<ProductQuantityAndDiscount> i = products.iterator(); i.hasNext();) {
                ProductQuantityAndDiscount p = i.next();
                if(product.getBarCode().equals(p.getProduct().getBarCode())) {
                    p.setQuantity(p.getQuantity()+Integer.valueOf(amount));
                    return true;
                }
            }
        try {
            this.products.add(
                new ProductQuantityAndDiscount(
                    amount,
                    0.0,
                    product 
                )
            );
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }

        return true;
    }

    // TODO: aumenta la quantità su scaffali. ProductType quantity?
    public boolean deleteProductFromSale(String productCode, int amount) {

        for(Iterator<ProductQuantityAndDiscount> i = products.iterator(); i.hasNext();) {
            ProductQuantityAndDiscount p = i.next();
            if(productCode.equals(p.getProduct().getBarCode())) {
                if(amount < p.getQuantity()) {
                    p.setQuantity(p.getQuantity()-Integer.valueOf(amount));
                    // controla che sia diminuito
                    return true;
                }
                else if(amount == p.getQuantity()) {
                    products.remove(p);
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean applyDiscountRateToProduct(String productCode, double discountRate) {
        
        for(Iterator<ProductQuantityAndDiscount> i = products.iterator(); i.hasNext();) {
            ProductQuantityAndDiscount p = i.next();
            if(productCode.equals(String.valueOf(p.getProduct().getId()))) {
                p.setDiscountRate(discountRate);
                return true;
            }
        }

        return false;
    }

    // TODO: db
    public boolean endSaleTransaction() {
    /**
     * open, closed, payed  
     */
        if (this.status.equals("closed")) {
            return false;
        }
        else {
            this.status = "closed";
            // TODO: scrivi su db ed eventuale return false  if there was a problem in registering the data
            return true;
        }
    }



}
