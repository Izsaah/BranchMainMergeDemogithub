package dao;

import dto.CustomerCareDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerCareDAO {

    // 1. Add new ticket
    public boolean sendTicket(CustomerCareDTO ticket) throws Exception {
        String sql = "INSERT INTO tblCustomerCares (userID, subject, content, status, reply) VALUES (?, ?, ?, 'Pending', '')";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ticket.getUserID());
            ps.setString(2, ticket.getSubject());
            ps.setString(3, ticket.getContent());
            return ps.executeUpdate() > 0;
        }
    }

    // 2. Admin reply to ticket
    public boolean replyTicket(int ticketID, String reply, String newStatus) throws Exception {
        String sql = "UPDATE tblCustomerCares SET reply = ?, status = ? WHERE ticketID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, reply);
            ps.setString(2, newStatus);
            ps.setInt(3, ticketID);
            return ps.executeUpdate() > 0;
        }
    }

    // 3. Get all tickets (admin view)
    public List<CustomerCareDTO> getAllTickets() throws Exception {
        List<CustomerCareDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCustomerCares ORDER BY ticketID DESC";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    // 4. Get tickets by user (user view)
    public List<CustomerCareDTO> getTicketsByUser(String userID) throws Exception {
        List<CustomerCareDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCustomerCares WHERE userID = ? ORDER BY ticketID DESC";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    // 5. Get a specific ticket (optional)
    public CustomerCareDTO getTicketByID(int ticketID) throws Exception {
        String sql = "SELECT * FROM tblCustomerCares WHERE ticketID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ticketID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    private CustomerCareDTO map(ResultSet rs) throws SQLException {
        return new CustomerCareDTO(
            rs.getInt("ticketID"),
            rs.getString("userID"),
            rs.getString("subject"),
            rs.getString("content"),
            rs.getString("status"),
            rs.getString("reply")
        );
    }
}
    