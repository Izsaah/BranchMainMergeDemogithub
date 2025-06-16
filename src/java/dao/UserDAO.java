package dao;

import dto.UserDTO;
import dto.UserDTO;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtil;

public class UserDAO {

    /**
     * Trả về User nếu login thành công, ngược lại trả về null. Không bắt
     * Exception âm thầm nữa, để controller log hoặc ném lên.
     */
    public static UserDTO login(String userID, String password)
            throws ClassNotFoundException, SQLException, Exception {
        // Debug đầu vào
        System.out.println("UserDAO.login(): userID=" + userID + ", password=" + password);

        String sql = "SELECT fullName, roleID FROM tblUsers WHERE userID = ? AND password = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, password);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    System.out.println("Login successful for " + userID + ", fullName=" + fullName + ", roleID=" + roleID);
                    // *** LƯU Ý: tham số cuối "***" chỉ dùng tạm, không quan trọng
                    return new UserDTO(userID, fullName, roleID);
                } else {
                    System.out.println("Login failed: no matching record");
                }
            }
        }
        return null;
    }

    public static List<UserDTO> getAllUsers() throws Exception {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblUsers";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UserDTO temp = new UserDTO(rs.getString("userID"), rs.getString("fullName"), rs.getString("roleID"), rs.getString("password"), rs.getString("phone"));
                list.add(temp);

            }
        }
        return list;
    }
}
