package dao;
import dto.CartDTO;

import java.sql.*;
import java.util.List;
import utils.DBUtil;

public class InvoiceDAO {
    public static boolean checkout(String userID, List<CartDTO> cartItems) {
        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);

            double total = 0;
            for (CartDTO c : cartItems) total += c.getTotal();

            String sql1 = "INSERT INTO tblInvoices(userID, totalAmount, status, createdDate) VALUES (?, ?, 'Pending', GETDATE())";
            ps1 = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, userID);
            ps1.setDouble(2, total);
            ps1.executeUpdate();
            rs = ps1.getGeneratedKeys();
            int invoiceID = 0;
            if (rs.next()) {
                invoiceID = rs.getInt(1);
            }

            String sql2 = "INSERT INTO tblInvoiceDetails(invoiceID, productID, quantity, price) VALUES (?, ?, ?, ?)";
            ps2 = con.prepareStatement(sql2);
            for (CartDTO item : cartItems) {
                ps2.setInt(1, invoiceID);
                ps2.setInt(2, item.getProductID());
                ps2.setInt(3, item.getQuantity());
                ps2.setDouble(4, item.getPrice());
                ps2.addBatch();
            }
            ps2.executeBatch();

            con.commit();
            return true;
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
        return false;
    }
}
