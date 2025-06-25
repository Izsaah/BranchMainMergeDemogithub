package dto;

public class ReturnDTO {
    private int returnID;
    private int invoiceID;
    private String reason;
    private String status;

    public ReturnDTO() {
    }

    public ReturnDTO(int returnID, int invoiceID, String reason, String status) {
        this.returnID = returnID;
        this.invoiceID = invoiceID;
        this.reason = reason;
        this.status = status;
    }


    public int getReturnID() {
        return returnID;
    }

    public void setReturnID(int returnID) {
        this.returnID = returnID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
    