package dto;

import java.sql.Date;
import java.util.List;

public class CartDTO {
    private int cartID;
    private String userID;
    private Date createdDate;
    private List<CartDetailsDTO> items; // optional: holds associated items

    public CartDTO() {}

    public CartDTO(int cartID, String userID, Date createdDate) {
        this.cartID = cartID;
        this.userID = userID;
        this.createdDate = createdDate;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<CartDetailsDTO> getItems() {
        return items;
    }

    public void setItems(List<CartDetailsDTO> items) {
        this.items = items;
    }
}
