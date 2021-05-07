package it.polito.ezshop.data;

import java.time.LocalDate;

public class EZBalanceOperation implements BalanceOperation {

    private Integer balanceId;
    private LocalDate date;
    private Double money;
    private String type;

    public EZBalanceOperation(String type, double money) {
        this.balanceId = -1; //TODO: Get from DB...
        this.type = type;
        this.money = money;
        this.date = LocalDate.now(); //TODO: ???
    }

    public EZBalanceOperation(Integer balanceId, LocalDate date, Double money, String type) {
        this.balanceId = balanceId;
        this.date = date;
        this.money = money;
        this.type = type;
    }

    @Override
    public int getBalanceId() {
        return balanceId;
    }

    @Override
    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
        
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
        
    }

    @Override
    public double getMoney() {
        return money.doubleValue();
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
    
}
