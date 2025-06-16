package dto;

import java.util.Date;

public class DeliveryDTO {
    private int deliveryID;
    private int invoiceID;
    private String address;
    private Date deliveryDate;
    private String status;

    public DeliveryDTO() {}

    public DeliveryDTO(int deliveryID, int invoiceID, String address, Date deliveryDate, String status) {
        this.deliveryID = deliveryID;
        this.invoiceID = invoiceID;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

}
