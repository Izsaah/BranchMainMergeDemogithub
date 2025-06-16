package dao;

import dto.ProductDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtil;

public class ProductDAO {
    public static boolean insert(ProductDTO product) {
        String sql = "INSERT INTO tblProducts(name, categoryID, price, quantity, sellerID, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getSellerID());
            ps.setString(6, product.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ProductDTO> getAll() {
        List<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductDTO p = new ProductDTO(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getInt("categoryID"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("sellerID"),
                    rs.getString("status")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
