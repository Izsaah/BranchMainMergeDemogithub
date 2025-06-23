/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.UsersDTO;
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
public class UsersDAO {
     public boolean addUser(UsersDTO user) {
        String sql = "INSERT INTO tblUsers(userID, fullName, roleID, password, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getRoleID());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Authenticate user
    public UsersDTO checkLogin(String userID, String password) {
        String sql = "SELECT * FROM tblUsers WHERE userID = ? AND password = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UsersDTO(
                    rs.getString("userID"),
                    rs.getString("fullName"),
                    rs.getString("roleID"),
                    rs.getString("password"),
                    rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get user by ID
    public UsersDTO getUserByID(String userID) {
        String sql = "SELECT * FROM tblUsers WHERE userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UsersDTO(
                    rs.getString("userID"),
                    rs.getString("fullName"),
                    rs.getString("roleID"),
                    rs.getString("password"),
                    rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user info
    public boolean updateUser(UsersDTO user) {
        String sql = "UPDATE tblUsers SET fullName = ?, roleID = ?, password = ?, phone = ? WHERE userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getRoleID());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getUserID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete user
    public boolean deleteUser(String userID) {
        String sql = "DELETE FROM tblUsers WHERE userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // List all users (admin view)
    public List<UsersDTO> getAllUsers() {
        List<UsersDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblUsers ORDER BY userID";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new UsersDTO(
                    rs.getString("userID"),
                    rs.getString("fullName"),
                    rs.getString("roleID"),
                    rs.getString("password"),
                    rs.getString("phone")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
