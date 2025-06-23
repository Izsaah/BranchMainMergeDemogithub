/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.DeliveriesDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtil;

/**
 *
 * @author ACER
 */
public class DeliveriesDAO {
     // Add a new delivery
    public boolean addDelivery(DeliveriesDTO delivery) {
        String sql = "INSERT INTO tblDeliveries(invoiceID, address, deliveryDate, status) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, delivery.getInvoiceID());
            ps.setString(2, delivery.getAddress());
            ps.setString(3, delivery.getDeliveryDate());
            ps.setString(4, delivery.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get delivery by deliveryID
    public DeliveriesDTO getDeliveryByID(int deliveryID) {
        String sql = "SELECT * FROM tblDeliveries WHERE deliveryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, deliveryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DeliveriesDTO(
                    rs.getInt("deliveryID"),
                    rs.getInt("invoiceID"),
                    rs.getString("address"),
                    rs.getString("deliveryDate"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get delivery by invoiceID (assumes 1-1 relationship)
    public DeliveriesDTO getDeliveryByInvoiceID(int invoiceID) {
        String sql = "SELECT * FROM tblDeliveries WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DeliveriesDTO(
                    rs.getInt("deliveryID"),
                    rs.getInt("invoiceID"),
                    rs.getString("address"),
                    rs.getString("deliveryDate"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update delivery status
    public boolean updateDeliveryStatus(int deliveryID, String newStatus) {
        String sql = "UPDATE tblDeliveries SET status = ? WHERE deliveryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, deliveryID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Admin: get all deliveries
    public List<DeliveriesDTO> getAllDeliveries() {
        List<DeliveriesDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblDeliveries ORDER BY deliveryDate DESC";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new DeliveriesDTO(
                    rs.getInt("deliveryID"),
                    rs.getInt("invoiceID"),
                    rs.getString("address"),
                    rs.getString("deliveryDate"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete delivery record (optional, admin only)
    public boolean deleteDelivery(int deliveryID) {
        String sql = "DELETE FROM tblDeliveries WHERE deliveryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, deliveryID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
