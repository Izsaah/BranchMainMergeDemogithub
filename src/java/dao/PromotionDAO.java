package dao;
import utils.DBUtil;
import java.sql.*;
public class PromotionDAO {
    public static boolean insert(String name, double percent, String start, String end, String status) {
        String sql = "INSERT INTO tblPromotions(name, discountPercent, startDate, endDate, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, percent);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setString(5, status);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
