/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.CartDetailsDTO;
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
public class CartDetailsDAO {
        
       public boolean addCartDetail(CartDetailsDTO cartDetail) {
        String sql = "INSERT INTO tblCartDetails(cartID, productID, quantity) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartDetail.getCartID());
            ps.setInt(2, cartDetail.getProductID());
            ps.setInt(3, cartDetail.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update quantity of a product in cart
    public boolean updateCartDetail(CartDetailsDTO cartDetail) {
        String sql = "UPDATE tblCartDetails SET quantity = ? WHERE cartID = ? AND productID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartDetail.getQuantity());
            ps.setInt(2, cartDetail.getCartID());
            ps.setInt(3, cartDetail.getProductID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove item from cart
    public boolean deleteCartDetail(int cartID, int productID) {
        String sql = "DELETE FROM tblCartDetails WHERE cartID = ? AND productID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ps.setInt(2, productID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all products in a cart
    public List<CartDetailsDTO> getCartDetailsByCartID(int cartID) {
        List<CartDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCartDetails WHERE cartID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CartDetailsDTO(
                    rs.getInt("cartID"),
                    rs.getInt("productID"),
                    rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get single cart item (if exists)
    public CartDetailsDTO getCartDetail(int cartID, int productID) {
        String sql = "SELECT * FROM tblCartDetails WHERE cartID = ? AND productID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ps.setInt(2, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartDetailsDTO(
                    rs.getInt("cartID"),
                    rs.getInt("productID"),
                    rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
