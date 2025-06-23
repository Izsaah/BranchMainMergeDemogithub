/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.CategoriesDTO;
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
public class CategoriesDAO {
     public boolean addCategory(CategoriesDTO category) {
        String sql = "INSERT INTO tblCategories(categoryName, description) VALUES (?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all categories
    public List<CategoriesDTO> getAllCategories() {
        List<CategoriesDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategories ORDER BY categoryID ASC";
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

    // Get a category by ID
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

    // Update category
    public boolean updateCategory(CategoriesDTO category) {
        String sql = "UPDATE tblCategories SET categoryName = ?, description = ? WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete category
    public boolean deleteCategory(int categoryID) {
        String sql = "DELETE FROM tblCategories WHERE categoryID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
