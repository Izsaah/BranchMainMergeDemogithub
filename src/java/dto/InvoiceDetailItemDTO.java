package dto;

public class InvoiceDetailItemDTO {
    private int productID;
    private String productName;
    private int quantity;
    private float price;

    public InvoiceDetailItemDTO() {
    }

    public InvoiceDetailItemDTO(int productID, String productName, int quantity, float price) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public float getSubtotal() {
        return price * quantity;
    }
}
