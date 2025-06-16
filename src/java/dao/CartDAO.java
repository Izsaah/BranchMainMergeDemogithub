package dao;

import dto.CartDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public static List<CartDTO> getCartByUser(String userID) {
        List<CartDTO> list = new ArrayList<>();
        String sql = "SELECT c.cartID, c.userID, cd.productID, p.name, cd.quantity, p.price " +
                     "FROM tblCarts c " +
                     "JOIN tblCartDetails cd ON c.cartID = cd.cartID " +
                     "JOIN tblProducts p ON cd.productID = p.productID " +
                     "WHERE c.userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartDTO dto = new CartDTO(
                        rs.getInt("cartID"),
                        rs.getString("userID"),
                        rs.getInt("productID"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
