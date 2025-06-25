package dao;

import dto.ReturnDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnDAO {

    public boolean createReturn(ReturnDTO r) throws Exception {
        String sql = "INSERT INTO tblReturns(invoiceID, reason, status) VALUES (?, ?, 'Pending')";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getInvoiceID());
            ps.setString(2, r.getReason());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateReturnStatus(int returnID, String status) throws Exception {
        String sql = "UPDATE tblReturns SET status = ? WHERE returnID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, returnID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ReturnDTO> getAllReturns() throws Exception {
        List<ReturnDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblReturns ORDER BY returnID DESC";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReturnDTO(
                    rs.getInt("returnID"),
                    rs.getInt("invoiceID"),
                    rs.getString("reason"),
                    rs.getString("status")
                ));
            }
        }
        return list;
    }
}
