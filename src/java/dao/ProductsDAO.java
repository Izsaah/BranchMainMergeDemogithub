package dao;

import dto.ProductsDTO;
import utils.DBUtil;
import java.sql.*;
import java.util.*;

public class ProductsDAO {

    public boolean addProduct(ProductsDTO product) throws Exception {
        String sql = "INSERT INTO tblProducts(name, categoryID, price, quantity, sellerID, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getSellerID());
            ps.setString(6, product.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public List<ProductsDTO> getAllProducts() throws Exception {
        List<ProductsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts ORDER BY productID DESC";
        try (Connection con = DBUtil.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public ProductsDTO getProductByID(int productID) throws Exception {
        String sql = "SELECT * FROM tblProducts WHERE productID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<ProductsDTO> getProductsByCategory(int categoryID) throws Exception {
        List<ProductsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<ProductsDTO> getProductsBySeller(String sellerID) throws Exception {
        List<ProductsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts WHERE sellerID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sellerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean updateProduct(ProductsDTO product) throws Exception {
        String sql = "UPDATE tblProducts SET name=?, categoryID=?, price=?, quantity=?, status=? WHERE productID=?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getStatus());
            ps.setInt(6, product.getProductID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(int productID) throws Exception {
        String sql = "DELETE FROM tblProducts WHERE productID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productID);
            return ps.executeUpdate() > 0;
        }
    }
public List<ProductsDTO> searchProducts(String keyword, Integer categoryID, String status, Float minPrice, Float maxPrice) {
    List<ProductsDTO> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM tblProducts WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (keyword != null && !keyword.trim().isEmpty()) {
        sql.append(" AND name LIKE ?");
        params.add("%" + keyword.trim() + "%");
    }
    if (categoryID != null && categoryID > 0) {
        sql.append(" AND categoryID = ?");
        params.add(categoryID);
    }
    if (status != null && !status.isEmpty()) {
        sql.append(" AND status = ?");
        params.add(status);
    }
    if (minPrice != null) {
        sql.append(" AND price >= ?");
        params.add(minPrice);
    }
    if (maxPrice != null) {
        sql.append(" AND price <= ?");
        params.add(maxPrice);
    }

    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(map(rs));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
public boolean reduceQuantity(int productID, int quantity) throws Exception {
    String sql = "UPDATE tblProducts SET quantity = quantity - ? WHERE productID = ? AND quantity >= ?";
    try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, quantity);
        ps.setInt(2, productID);
        ps.setInt(3, quantity);
        return ps.executeUpdate() > 0;
    }
}

    private ProductsDTO map(ResultSet rs) throws SQLException {
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
}
