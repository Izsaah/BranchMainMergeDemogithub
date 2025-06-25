package dao;

import dto.DeliveryDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {

    // Get all deliveries
    public List<DeliveryDTO> getAllDeliveries() throws Exception {
        List<DeliveryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblDeliveries ORDER BY deliveryDate DESC";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    // Get delivery by invoiceID
    public DeliveryDTO getDeliveryByInvoiceID(int invoiceID) throws Exception {
        String sql = "SELECT * FROM tblDeliveries WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    // Add a new delivery
    public boolean addDelivery(DeliveryDTO delivery) throws Exception {
        String sql = "INSERT INTO tblDeliveries (invoiceID, address, deliveryDate, status) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, delivery.getInvoiceID());
            ps.setString(2, delivery.getAddress());
            ps.setDate(3, delivery.getDeliveryDate());
            ps.setString(4, delivery.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    // Update existing delivery status and date
    public boolean updateDelivery(DeliveryDTO delivery) throws Exception {
        String sql = "UPDATE tblDeliveries SET address = ?, deliveryDate = ?, status = ? WHERE deliveryID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, delivery.getAddress());
            ps.setDate(2, delivery.getDeliveryDate());
            ps.setString(3, delivery.getStatus());
            ps.setInt(4, delivery.getDeliveryID());
            return ps.executeUpdate() > 0;
        }
    }

    // Delete a delivery (optional)
    public boolean deleteDelivery(int deliveryID) throws Exception {
        String sql = "DELETE FROM tblDeliveries WHERE deliveryID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, deliveryID);
            return ps.executeUpdate() > 0;
        }
    }

    // Mapping helper method
    private DeliveryDTO map(ResultSet rs) throws SQLException {
        return new DeliveryDTO(
                rs.getInt("deliveryID"),
                rs.getInt("invoiceID"),
                rs.getString("address"),
                rs.getDate("deliveryDate"),
                rs.getString("status")
        );
    }
}
