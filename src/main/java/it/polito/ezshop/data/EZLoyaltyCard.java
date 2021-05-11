package it.polito.ezshop.data;

public class EZLoyaltyCard {
    
    private Integer points;
    private String cardNumber;
    
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void addPointsOnCard(int pointsToBeAdded){
        this.points += pointsToBeAdded;
    }
}