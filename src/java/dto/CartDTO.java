package dto;

public class CartDTO {
    private int cartID;
    private String userID;
    private String productName;
    private int productID;
    private int quantity;
    private double price;

    public CartDTO() {}

    public CartDTO(int cartID, String userID, int productID, String productName, int quantity, double price) {
        this.cartID = cartID;
        this.userID = userID;
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getCartID() { return cartID; }
    public String getUserID() { return userID; }
    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public double getTotal() {
        return quantity * price;
    }
}
