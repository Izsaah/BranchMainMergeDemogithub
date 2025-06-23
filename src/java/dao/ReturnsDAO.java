/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.ReturnsDTO;
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
public class ReturnsDAO {
      // Add new return request
    public boolean addReturn(ReturnsDTO returnsDTO) {
        String sql = "INSERT INTO tblReturns(invoiceID, reason, status) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, returnsDTO.getInvoiceID());
            ps.setString(2, returnsDTO.getReason());
            ps.setString(3, returnsDTO.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get return request by returnID
    public ReturnsDTO getReturnByID(int returnID) {
        String sql = "SELECT * FROM tblReturns WHERE returnID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, returnID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ReturnsDTO(
                    rs.getInt("returnID"),
                    rs.getInt("invoiceID"),
                    rs.getString("reason"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all return requests (admin view)
    public List<ReturnsDTO> getAllReturns() {
        List<ReturnsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblReturns ORDER BY returnID DESC";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ReturnsDTO(
                    rs.getInt("returnID"),
                    rs.getInt("invoiceID"),
                    rs.getString("reason"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get return by invoiceID (if 1 return per invoice)
    public ReturnsDTO getReturnByInvoiceID(int invoiceID) {
        String sql = "SELECT * FROM tblReturns WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ReturnsDTO(
                    rs.getInt("returnID"),
                    rs.getInt("invoiceID"),
                    rs.getString("reason"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Admin: update return status
    public boolean updateReturnStatus(int returnID, String newStatus) {
        String sql = "UPDATE tblReturns SET status = ? WHERE returnID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, returnID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Optional: delete return request
    public boolean deleteReturn(int returnID) {
        String sql = "DELETE FROM tblReturns WHERE returnID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, returnID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
