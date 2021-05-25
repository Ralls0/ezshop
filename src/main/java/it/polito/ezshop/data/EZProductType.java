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
        this.quantity = quantity;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String getProductDescription() {
        return description;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.description = productDescription;
    }

    @Override
    public String getBarCode() {
        return productCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.productCode = barCode;
    }

    @Override
    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
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

    public int hashCode(){
        return id.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof EZProductType))
            return false;
        EZProductType otherProduct = (EZProductType) other;

        return this.id == otherProduct.getId();
    }
}
