package dao;
import utils.DBUtil;
import java.sql.*;
public class ReturnDAO {
    public static boolean insert(int invoiceID, String reason) {
        String sql = "INSERT INTO tblReturns(invoiceID, reason, status) VALUES (?, ?, 'Pending')";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ps.setString(2, reason);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
