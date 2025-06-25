package dao;

import dto.InvoiceDetailsDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailsDAO {

    public boolean addInvoiceDetail(int invoiceID, int productID, int quantity, float price) throws Exception {
        String sql = "INSERT INTO tblInvoiceDetails (invoiceID, productID, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ps.setInt(2, productID);
            ps.setInt(3, quantity);
            ps.setFloat(4, price);
            return ps.executeUpdate() > 0;
        }
    }

    public List<InvoiceDetailsDTO> getInvoiceDetails(int invoiceID) throws Exception {
        List<InvoiceDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblInvoiceDetails WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InvoiceDetailsDTO(
                    rs.getInt("invoiceID"),
                    rs.getInt("productID"),
                    rs.getInt("quantity"),
                    rs.getFloat("price")
                ));
            }
        }
        return list;
    }

    public boolean deleteByInvoiceID(int invoiceID) throws Exception {
        String sql = "DELETE FROM tblInvoiceDetails WHERE invoiceID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<InvoiceDetailsDTO> getAllInvoiceDetails() throws Exception {
        List<InvoiceDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblInvoiceDetails";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InvoiceDetailsDTO(
                    rs.getInt("invoiceID"),
                    rs.getInt("productID"),
                    rs.getInt("quantity"),
                    rs.getFloat("price")
                ));
            }
        }
        return list;
    }
}
