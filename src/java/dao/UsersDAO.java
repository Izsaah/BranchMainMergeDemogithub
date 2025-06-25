package dao;

import dto.UsersDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    // Thêm người dùng mới
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
            System.err.println("Add User Failed: " + e.getMessage());
            return false;
        }
    }

    // Đăng nhập (check userID & password)
    public UsersDTO checkLogin(String userID, String password) {
        String sql = "SELECT fullName, roleID, phone FROM tblUsers WHERE userID = ? AND password = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UsersDTO(
                        userID,
                        rs.getString("fullName"),
                        rs.getString("roleID"),
                        null, // Không trả password về
                        rs.getString("phone")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
        }
        return null;
    }

    // Lấy user theo ID (ví dụ để edit)
    public UsersDTO getUserByID(String userID) {
        String sql = "SELECT * FROM tblUsers WHERE userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UsersDTO(
                        rs.getString("userID"),
                        rs.getString("fullName"),
                        rs.getString("roleID"),
                        rs.getString("password"),
                        rs.getString("phone")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Get user by ID failed: " + e.getMessage());
        }
        return null;
    }

    // Cập nhật thông tin người dùng
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
            System.err.println("Update user failed: " + e.getMessage());
            return false;
        }
    }

    // Xoá người dùng
    public boolean deleteUser(String userID) {
        String sql = "DELETE FROM tblUsers WHERE userID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Delete user failed: " + e.getMessage());
            return false;
        }
    }

    // Trả về danh sách tất cả người dùng (Admin view)
    public List<UsersDTO> getAllUsers() {
        List<UsersDTO> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, roleID, phone FROM tblUsers ORDER BY userID";

        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new UsersDTO(
                    rs.getString("userID"),
                    rs.getString("fullName"),
                    rs.getString("roleID"),
                    null, // không trả password
                    rs.getString("phone")
                ));
            }

        } catch (Exception e) {
            System.err.println("Get all users failed: " + e.getMessage());
        }

        return list;
    }
    
    
    public List<UsersDTO> searchUsers(String keyword) {
    List<UsersDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM tblUsers WHERE userID LIKE ? OR fullName LIKE ? OR roleID LIKE ?";
    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        String kw = "%" + keyword + "%";
        ps.setString(1, kw);
        ps.setString(2, kw);
        ps.setString(3, kw);
        ResultSet rs = ps.executeQuery();
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
