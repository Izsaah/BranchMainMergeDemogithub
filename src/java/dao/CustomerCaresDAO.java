/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.CustomerCaresDTO;
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
public class CustomerCaresDAO {
    
     public boolean submitTicket(CustomerCaresDTO ticket) {
        String sql = "INSERT INTO tblCustomerCares(userID, subject, content, status, reply) VALUES (?, ?, ?, ?, NULL)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ticket.getUserID());
            ps.setString(2, ticket.getSubject());
            ps.setString(3, ticket.getContent());
            ps.setString(4, ticket.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all tickets submitted by a specific user
    public List<CustomerCaresDTO> getTicketsByUser(String userID) {
        List<CustomerCaresDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCustomerCares WHERE userID = ? ORDER BY ticketID DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CustomerCaresDTO(
                    rs.getInt("ticketID"),
                    rs.getString("userID"),
                    rs.getString("subject"),
                    rs.getString("content"),
                    rs.getString("status"),
                    rs.getString("reply")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get all tickets (admin view)
    public List<CustomerCaresDTO> getAllTickets() {
        List<CustomerCaresDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCustomerCares ORDER BY ticketID DESC";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CustomerCaresDTO(
                    rs.getInt("ticketID"),
                    rs.getString("userID"),
                    rs.getString("subject"),
                    rs.getString("content"),
                    rs.getString("status"),
                    rs.getString("reply")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Reply to a ticket (admin function)
    public boolean replyToTicket(int ticketID, String replyContent) {
        String sql = "UPDATE tblCustomerCares SET reply = ?, status = 'Replied' WHERE ticketID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, replyContent);
            ps.setInt(2, ticketID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get ticket by ID
    public CustomerCaresDTO getTicketByID(int ticketID) {
        String sql = "SELECT * FROM tblCustomerCares WHERE ticketID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ticketID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CustomerCaresDTO(
                    rs.getInt("ticketID"),
                    rs.getString("userID"),
                    rs.getString("subject"),
                    rs.getString("content"),
                    rs.getString("status"),
                    rs.getString("reply")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete ticket (optional, admin only)
    public boolean deleteTicket(int ticketID) {
        String sql = "DELETE FROM tblCustomerCares WHERE ticketID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ticketID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
