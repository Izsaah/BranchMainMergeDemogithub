package controller;

import dao.CustomerCareDAO;
import dto.CustomerCareDTO;
import dto.UsersDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerCareController")
public class CustomerCareController extends HttpServlet {

    private final CustomerCareDAO dao = new CustomerCareDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handle(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handle(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");

        if (action == null || user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            switch (action) {

                case "SendTicket": {
                    String subject = request.getParameter("subject");
                    String content = request.getParameter("content");

                    CustomerCareDTO ticket = new CustomerCareDTO(0, user.getUserID(), subject, content, "Pending", "");
                    dao.sendTicket(ticket);

                    request.setAttribute("successMsg", "Your ticket has been submitted.");
                    List<CustomerCareDTO> myTickets = dao.getTicketsByUser(user.getUserID());
                    request.setAttribute("TICKET_LIST", myTickets);
                    request.getRequestDispatcher("customer-tickets.jsp").forward(request, response);
                    break;
                }

                case "ReplyTicket": {
                    if (!"AD".equals(user.getRoleID())) {
                        response.sendRedirect("unauthorized.jsp");
                        return;
                    }

                    int ticketID = Integer.parseInt(request.getParameter("ticketID"));
                    String reply = request.getParameter("reply");
                    String status = request.getParameter("status");

                    dao.replyTicket(ticketID, reply, status);

                    request.setAttribute("successMsg", "Reply sent successfully.");
                    List<CustomerCareDTO> allTickets = dao.getAllTickets();
                    request.setAttribute("TICKET_LIST", allTickets);
                    request.getRequestDispatcher("admin-customer-care.jsp").forward(request, response);
                    break;
                }

                default:
                    request.setAttribute("ERROR", "Unknown action.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Customer care error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
