package dao;
import dto.DeliveryDTO;
import utils.DBUtil;
import java.sql.*;

public class DeliveryDAO {

    public static DeliveryDTO getByInvoiceID(int invoiceID) {
        String sql = "SELECT deliveryID, invoiceID, address, deliveryDate, status FROM tblDeliveries WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DeliveryDTO(
                    rs.getInt("deliveryID"),
                    rs.getInt("invoiceID"),
                    rs.getString("address"),
                    rs.getDate("deliveryDate"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
