/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author ACER
 */
public class CartsDTO {
    private int cartID;
    private String userID;
    private Date CreatedDate;

    public CartsDTO(int cartID, String userID, Date reatedDate) {
        this.cartID = cartID;
        this.userID = userID;
        this.CreatedDate = reatedDate;
    }


    
    

    public CartsDTO() {
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
        return CreatedDate;
    }

    public void setCreatedDate(Date reatedDate) {
        this.CreatedDate = reatedDate;
    }


    
}
