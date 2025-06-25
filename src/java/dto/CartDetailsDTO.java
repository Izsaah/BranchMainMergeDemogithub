package dto;

public class CartDetailsDTO {
    private int cartID;
    private int productID;
    private int quantity;

    private ProductsDTO product; // optional: for displaying product info

    public CartDetailsDTO() {}

    public CartDetailsDTO(int cartID, int productID, int quantity) {
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
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

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }
}
