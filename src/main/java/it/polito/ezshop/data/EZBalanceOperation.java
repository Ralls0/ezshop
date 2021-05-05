package it.polito.ezshop.data;

import java.time.LocalDate;

public class EZBalanceOperation implements BalanceOperation {

    private Integer balanceId;
    private LocalDate date;
    private Double money;
    private String type;

    public EZBalanceOperation(String type, double money) {
        this.balanceId = Integer.valueOf(-1);
        this.type = type;
        this.money = money;
        this.date = LocalDate.now(); //TODO: ???
    }

    @Override
    public int getBalanceId() {
        return balanceId.intValue();
    }

    @Override
    public void setBalanceId(int balanceId) {
        this.balanceId = Integer.valueOf(balanceId);
        
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
        this.money = Double.valueOf(money);
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
