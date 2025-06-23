package controller;

import dao.CustomerCaresDAO;
import dto.CustomerCaresDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerCareController")
public class CustomerCaresController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        CustomerCaresDAO dao = new CustomerCaresDAO();

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("ERROR", "Action is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            switch (action.trim()) {

                case "CustomerSupport": {
                    // View all tickets (admin only or per user session logic)
                    List<CustomerCaresDTO> tickets = dao.getAllTickets();
                    request.setAttribute("TICKET_LIST", tickets);
                    request.getRequestDispatcher("customerSupport.jsp").forward(request, response);
                    break;
                }

                case "SubmitTicket": {
                    String userID = request.getParameter("userID");
                    String subject = request.getParameter("subject");
                    String content = request.getParameter("content");

                    CustomerCaresDTO ticket = new CustomerCaresDTO();
                    ticket.setUserID(userID);
                    ticket.setSubject(subject);
                    ticket.setContent(content);
                    ticket.setStatus("Pending");
                    ticket.setReply(null);

                    boolean inserted = dao.submitTicket(ticket);

                    if (inserted) {
                        response.sendRedirect("MainController?action=CustomerSupport");
                    } else {
                        request.setAttribute("ERROR", "Failed to submit support ticket.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                }

                case "ReplyTicket": {
                    int ticketID = Integer.parseInt(request.getParameter("ticketID"));
                    String reply = request.getParameter("reply");

                    boolean updated = dao.replyToTicket(ticketID, reply);

                    if (updated) {
                        response.sendRedirect("MainController?action=CustomerSupport");
                    } else {
                        request.setAttribute("ERROR", "Failed to reply to ticket.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                }

                default: {
                    request.setAttribute("ERROR", "Unknown action: " + action);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "CustomerCareController error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
