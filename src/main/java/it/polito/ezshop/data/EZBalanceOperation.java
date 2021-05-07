package it.polito.ezshop.data;

import java.time.LocalDate;

public class EZBalanceOperation implements BalanceOperation{

    private Integer balanceID;
    private LocalDate date;
    private double amount;
    private String type;

    public EZBalanceOperation(Integer balanceID, LocalDate date, double amount, String type) {
        this.balanceID = balanceID;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public int getBalanceId() {
        return 0;
    }

    @Override
    public void setBalanceId(int balanceId) {

    }

    @Override
    public LocalDate getDate() {
        return null;
    }

    @Override
    public void setDate(LocalDate date) {

    }

    @Override
    public double getMoney() {
        return 0;
    }

    @Override
    public void setMoney(double money) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {

    }
}
