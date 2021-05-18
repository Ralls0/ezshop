package it.polito.ezshop.data;

public class EZCustomer implements Customer{

    private Integer id;
    private String name;
    private String card;
    private Integer points;

    public EZCustomer(Integer id, String name, String card, Integer points) {
        this.id = id;
        this.name = name;
        this.card = card;
        this.points = points;
    }

    @Override
    public String getCustomerName() {
        return name;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.name = customerName;
    }

    @Override
    public String getCustomerCard() {
        return card;
    }

    @Override
    public void setCustomerCard(String customerCard) {
        this.card = customerCard;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        if(id > 0)
            this.id = id;
    }

    @Override
    public Integer getPoints() {
        return points;
    }

    @Override
    public void setPoints(Integer points) {
        this.points = points;
    }
}
