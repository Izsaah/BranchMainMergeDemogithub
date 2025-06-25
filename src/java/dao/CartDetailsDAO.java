package dao;

import dto.CartDetailsDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDetailsDAO {

    public boolean addOrUpdateItem(int cartID, int productID, int quantity) throws Exception {
        String checkSql = "SELECT quantity FROM tblCartDetails WHERE cartID = ? AND productID = ?";
        String insertSql = "INSERT INTO tblCartDetails (cartID, productID, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE tblCartDetails SET quantity = ? WHERE cartID = ? AND productID = ?";

        try (Connection con = DBUtil.getConnection(); PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
            checkStmt.setInt(1, cartID);
            checkStmt.setInt(2, productID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingQty = rs.getInt("quantity");
                try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, existingQty + quantity);
                    updateStmt.setInt(2, cartID);
                    updateStmt.setInt(3, productID);
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, cartID);
                    insertStmt.setInt(2, productID);
                    insertStmt.setInt(3, quantity);
                    return insertStmt.executeUpdate() > 0;
                }
            }
        }
    }

    public List<CartDetailsDTO> getCartItems(int cartID) throws Exception {
        List<CartDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCartDetails WHERE cartID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CartDetailsDTO(
                    rs.getInt("cartID"),
                    rs.getInt("productID"),
                    rs.getInt("quantity")
                ));
            }
        }
        return list;
    }

    public boolean updateItemQuantity(int cartID, int productID, int newQuantity) throws Exception {
        String sql = "UPDATE tblCartDetails SET quantity = ? WHERE cartID = ? AND productID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, cartID);
            ps.setInt(3, productID);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeItem(int cartID, int productID) throws Exception {
        String sql = "DELETE FROM tblCartDetails WHERE cartID = ? AND productID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ps.setInt(2, productID);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean clearCart(int cartID) throws Exception {
        String sql = "DELETE FROM tblCartDetails WHERE cartID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            return ps.executeUpdate() > 0;
        }
    }
}
