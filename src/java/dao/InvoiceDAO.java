package dao;

import dto.InvoiceDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public int createInvoice(String userID, float totalAmount) throws Exception {
        String sql = "INSERT INTO tblInvoices (userID, totalAmount, status, createdDate) VALUES (?, ?, 'Pending', GETDATE())";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, userID);
            ps.setFloat(2, totalAmount);

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // return invoiceID
                }
            }
        }
        return -1;
    }

    public InvoiceDTO getInvoiceByID(int invoiceID) throws Exception {
        String sql = "SELECT * FROM tblInvoices WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new InvoiceDTO(
                    rs.getInt("invoiceID"),
                    rs.getString("userID"),
                    rs.getFloat("totalAmount"),
                    rs.getString("status"),
                    rs.getDate("createdDate")
                );
            }
        }
        return null;
    }

    public List<InvoiceDTO> getInvoicesByUser(String userID) throws Exception {
        List<InvoiceDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblInvoices WHERE userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InvoiceDTO(
                    rs.getInt("invoiceID"),
                    rs.getString("userID"),
                    rs.getFloat("totalAmount"),
                    rs.getString("status"),
                    rs.getDate("createdDate")
                ));
            }
        }
        return list;
    }

    public boolean updateStatus(int invoiceID, String newStatus) throws Exception {
        String sql = "UPDATE tblInvoices SET status = ? WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, invoiceID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<InvoiceDTO> getAllInvoices() throws Exception {
        List<InvoiceDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblInvoices";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InvoiceDTO(
                    rs.getInt("invoiceID"),
                    rs.getString("userID"),
                    rs.getFloat("totalAmount"),
                    rs.getString("status"),
                    rs.getDate("createdDate")
                ));
            }
        }
        return list;
    }
}
