/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class InvoicesDTO {
    private int invoiceID;
    private String userID;
    private float totalAmount;
    private String status;
    private String createdDate;

    public InvoicesDTO(int invoiceID, String userID, float totalAmount, String status, String createdDate) {
        this.invoiceID = invoiceID;
        this.userID = userID;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdDate = createdDate;
    }

    public InvoicesDTO() {
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
}
