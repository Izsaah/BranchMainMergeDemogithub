package dao;

import dto.CartDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public int createCartIfNotExists(String userID) throws Exception {
        int cartID = getCartIDByUser(userID);
        if (cartID > 0) return cartID;

        String sql = "INSERT INTO tblCarts (userID, createdDate) VALUES (?, GETDATE())";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userID);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public int getCartIDByUser(String userID) throws Exception {
        String sql = "SELECT cartID FROM tblCarts WHERE userID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("cartID");
        }
        return -1;
    }

    public CartDTO getCartByUser(String userID) throws Exception {
        String sql = "SELECT * FROM tblCarts WHERE userID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartDTO(
                    rs.getInt("cartID"),
                    rs.getString("userID"),
                    rs.getDate("createdDate")
                );
            }
        }
        return null;
    }

    public List<CartDTO> getAllCarts() throws Exception {
        List<CartDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCarts";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CartDTO(
                    rs.getInt("cartID"),
                    rs.getString("userID"),
                    rs.getDate("createdDate")
                ));
            }
        }
        return list;
    }

    public boolean deleteCartByUser(String userID) throws Exception {
        String sql = "DELETE FROM tblCarts WHERE userID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            return ps.executeUpdate() > 0;
        }
    }
}
