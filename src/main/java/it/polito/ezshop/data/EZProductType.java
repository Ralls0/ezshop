package it.polito.ezshop.data;

public class EZProductType implements ProductType{

    private Integer id;
    private Integer quantity;
    private String productCode;
    private String description;
    private String note;
    private String location;
    private Double pricePerUnit;

    public EZProductType(Integer id, Integer quantity, String productCode, String description, String note, String location, Double pricePerUnit) {
        this.id = id;
        this.quantity = quantity;
        this.productCode = productCode;
        this.description = description;
        this.note = note;
        this.location = location;
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {

    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {

    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {

    }

    @Override
    public String getProductDescription() {
        return description;
    }

    @Override
    public void setProductDescription(String productDescription) {

    }

    @Override
    public String getBarCode() {
        return productCode;
    }

    @Override
    public void setBarCode(String barCode) {

    }

    @Override
    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit) {

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {

    }
}
