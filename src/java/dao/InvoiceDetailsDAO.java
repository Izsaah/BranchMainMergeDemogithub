/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.InvoiceDetailsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtil;

/**
 *
 * @author ACER
 */
public class InvoiceDetailsDAO {
    // Add item to invoice (during checkout)

    public boolean addInvoiceDetail(InvoiceDetailsDTO detail) {
        String sql = "INSERT INTO tblInvoiceDetails(invoiceID, productID, quantity, price) VALUES (?, ?, ?, ?)";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detail.getInvoiceID());
            ps.setInt(2, detail.getProductID());
            ps.setInt(3, detail.getQuantity());
            ps.setFloat(4, detail.getPrice());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 public List<InvoiceDetailsDTO> getAllInvoiceDetails() {
    List<InvoiceDetailsDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM tblInvoiceDetails";

    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            InvoiceDetailsDTO detail = new InvoiceDetailsDTO(
                rs.getInt("invoiceID"),
                rs.getInt("productID"),
                rs.getInt("quantity"),
                rs.getFloat("price")
            );
            list.add(detail);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    // Get all invoice items by invoiceID
    public List<InvoiceDetailsDTO> getInvoiceDetailsByInvoiceID(int invoiceID) {
        List<InvoiceDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblInvoiceDetails WHERE invoiceID = ?";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get single item in an invoice (optional)
    public InvoiceDetailsDTO getDetail(int invoiceID, int productID) {
        String sql = "SELECT * FROM tblInvoiceDetails WHERE invoiceID = ? AND productID = ?";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ps.setInt(2, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new InvoiceDetailsDTO(
                        rs.getInt("invoiceID"),
                        rs.getInt("productID"),
                        rs.getInt("quantity"),
                        rs.getFloat("price")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete all items from an invoice (rare, optional)
    public boolean deleteDetailsByInvoiceID(int invoiceID) {
        String sql = "DELETE FROM tblInvoiceDetails WHERE invoiceID = ?";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
