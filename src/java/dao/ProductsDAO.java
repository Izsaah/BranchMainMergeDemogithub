/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.ProductsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtil;
/**
 *
 * @author ACER
 */
public class ProductsDAO {
    
    // Add new product
    public boolean addProduct(ProductsDTO product) {
        String sql = "INSERT INTO tblProducts(name, categoryID, price, quantity, sellerID, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getSellerID());
            ps.setString(6, product.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all products
    public List<ProductsDTO> getAllProducts() {
        List<ProductsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts ORDER BY productID DESC";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ProductsDTO(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getInt("categoryID"),
                    rs.getFloat("price"),
                    rs.getInt("quantity"),
                    rs.getString("sellerID"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get product by ID
    public ProductsDTO getProductByID(int productID) {
        String sql = "SELECT * FROM tblProducts WHERE productID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ProductsDTO(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getInt("categoryID"),
                    rs.getFloat("price"),
                    rs.getInt("quantity"),
                    rs.getString("sellerID"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get products by category
    public List<ProductsDTO> getProductsByCategory(int categoryID) {
        List<ProductsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ProductsDTO(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getInt("categoryID"),
                    rs.getFloat("price"),
                    rs.getInt("quantity"),
                    rs.getString("sellerID"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get products by seller
    public List<ProductsDTO> getProductsBySeller(String sellerID) {
        List<ProductsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts WHERE sellerID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sellerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ProductsDTO(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getInt("categoryID"),
                    rs.getFloat("price"),
                    rs.getInt("quantity"),
                    rs.getString("sellerID"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update product
    public boolean updateProduct(ProductsDTO product) {
        String sql = "UPDATE tblProducts SET name = ?, categoryID = ?, price = ?, quantity = ?, status = ? WHERE productID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getStatus());
            ps.setInt(6, product.getProductID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete product
    public boolean deleteProduct(int productID) {
        String sql = "DELETE FROM tblProducts WHERE productID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
