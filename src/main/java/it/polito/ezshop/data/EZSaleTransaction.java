package it.polito.ezshop.data;

import java.util.List;

public class EZSaleTransaction implements SaleTransaction{
    private Integer ticketNumber;
    private List<TicketEntry> entries;
    private double discountRate;
    private double getPrice;

    public EZSaleTransaction(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void addTicketEntry(TicketEntry ticketEntry) {
        entries.add(ticketEntry);
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
        return 0;
    }

    @Override
    public void setDiscountRate(double discountRate) {

    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public void setPrice(double price) {

    }
}
