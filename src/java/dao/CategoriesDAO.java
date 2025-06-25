package dao;

import dto.CategoriesDTO;
import dto.CategoriesDTO;
import utils.DBUtil;
import java.sql.*;
import java.util.*;

public class CategoriesDAO {
    public boolean addCategory(CategoriesDTO cat) {
        String sql = "INSERT INTO tblCategories(categoryName, description) VALUES (?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cat.getCategoryName());
            ps.setString(2, cat.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(CategoriesDTO cat) {
        String sql = "UPDATE tblCategories SET categoryName = ?, description = ? WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cat.getCategoryName());
            ps.setString(2, cat.getDescription());
            ps.setInt(3, cat.getCategoryID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(int categoryID) {
        String sql = "DELETE FROM tblCategories WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CategoriesDTO getCategoryByID(int categoryID) {
        String sql = "SELECT * FROM tblCategories WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CategoriesDTO(
                    rs.getInt("categoryID"),
                    rs.getString("categoryName"),
                    rs.getString("description")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CategoriesDTO> getAllCategories() {
        List<CategoriesDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategories ORDER BY categoryID DESC";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CategoriesDTO(
                    rs.getInt("categoryID"),
                    rs.getString("categoryName"),
                    rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CategoriesDTO> searchCategories(String keyword) {
        List<CategoriesDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategories WHERE categoryName LIKE ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CategoriesDTO(
                    rs.getInt("categoryID"),
                    rs.getString("categoryName"),
                    rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
}
