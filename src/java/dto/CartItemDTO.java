package dto;

public class CartItemDTO {
    private int productID;
    private String name;
    private float price;
    private int quantity;

    public CartItemDTO(int productID, String name, float price, int quantity) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getTotal() {
        return price * quantity;
    }
}
