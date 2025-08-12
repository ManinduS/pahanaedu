package model;

public class Item {
    private int id;
    private String name;
    private String description;
    private java.math.BigDecimal price;
    private int quantity;

    public Item() {}
    public Item(int id, String name, String description, java.math.BigDecimal price, int quantity) {
        this.id = id; this.name = name; this.description = description; this.price = price; this.quantity = quantity;
    }
    public Item(String name, String description, java.math.BigDecimal price, int quantity) {
        this.name = name; this.description = description; this.price = price; this.quantity = quantity;
    }
    // getters/setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public java.math.BigDecimal getPrice(){return price;} public void setPrice(java.math.BigDecimal price){this.price=price;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
}
