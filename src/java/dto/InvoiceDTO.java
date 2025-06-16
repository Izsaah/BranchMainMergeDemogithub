package dto;

public class InvoiceDTO {
    private int invoiceID;
    private String userID;
    private double totalAmount;
    private String status;
    private String createdDate;

    public InvoiceDTO() {}

    public InvoiceDTO(int invoiceID, String userID, double totalAmount, String status, String createdDate) {
        this.invoiceID = invoiceID;
        this.userID = userID;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdDate = createdDate;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getInvoiceID() { return invoiceID; }
    public String getUserID() { return userID; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getCreatedDate() { return createdDate; }
}
