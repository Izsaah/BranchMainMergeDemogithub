package dao;

import dto.CartsDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartsDAO {

    // Create a new cart and return the generated cart ID
    public int createCart(CartsDTO cart) {
    String sql = "INSERT INTO tblCarts(userID, createdDate) VALUES (?, ?)";
    String getIdSQL = "SELECT SCOPE_IDENTITY() AS newID;";

    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         Statement stmt = con.createStatement()) {

        ps.setString(1, cart.getUserID());
        ps.setDate(2, cart.getCreatedDate());
        ps.executeUpdate();

        ResultSet rs = stmt.executeQuery(getIdSQL);
        if (rs.next()) {
            return rs.getInt("newID");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1;
}

    // Get the most recent cart for a user
    public CartsDTO getLatestCartByUser(String userID) {
        String sql = "SELECT TOP 1 * FROM tblCarts WHERE userID = ? ORDER BY cartID DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartsDTO(
                        rs.getInt("cartID"),
                        rs.getString("userID"),
                        rs.getDate("createdDate")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get a cart by its ID
    public CartsDTO getCartByID(int cartID) {
        String sql = "SELECT * FROM tblCarts WHERE cartID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartsDTO(
                        rs.getInt("cartID"),
                        rs.getString("userID"),
                        rs.getDate("createdDate")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all carts by a user
    public List<CartsDTO> getAllCartsByUser(String userID) {
        List<CartsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCarts WHERE userID = ? ORDER BY cartID DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CartsDTO(
                        rs.getInt("cartID"),
                        rs.getString("userID"),
                        rs.getDate("createdDate")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
public List<CartsDTO> getAllCarts() {
    List<CartsDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM tblCarts ORDER BY cartID DESC";

    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new CartsDTO(
                    rs.getInt("cartID"),
                    rs.getString("userID"),
                    rs.getDate("createdDate")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    // Delete a cart
    public boolean deleteCart(int cartID) {
        String sql = "DELETE FROM tblCarts WHERE cartID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
