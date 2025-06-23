/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class InvoiceDetailsDTO {
      private int invoiceID;
    private int productID;
    private int quantity;
    private float price;

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public InvoiceDetailsDTO() {
    }

    public InvoiceDetailsDTO(int invoiceID, int productID, int quantity, float price) {
        this.invoiceID = invoiceID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }
    
}
