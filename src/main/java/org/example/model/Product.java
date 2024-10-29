package org.example.model;

public class Product {

    private Long productID;
    private String name;
    private double price;
    private double quantity;

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "Product{id=" + productID + ", name='" + name + "', price=" + price + ", quantity=" + quantity +"}";
    }
}
