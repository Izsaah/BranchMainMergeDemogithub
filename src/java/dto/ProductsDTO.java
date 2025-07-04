/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class ProductsDTO {
 private int productID;
    private String name;
    private int categoryID;
    private float price;
    private int quantity;
    private String sellerID;
    private String status;

    public ProductsDTO() {
    }

    public ProductsDTO(int productID, String name, int categoryID, float price, int quantity, String sellerID, String status) {
        this.productID = productID;
        this.name = name;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.sellerID = sellerID;
        this.status = status;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
 
    
    
}
