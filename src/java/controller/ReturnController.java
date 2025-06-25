package controller;

import dao.ReturnDAO;
import dto.ReturnDTO;
import dto.UsersDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/ReturnController")
public class ReturnController extends HttpServlet {

    private final ReturnDAO returnDAO = new ReturnDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "RequestReturn": {
                    int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
                    String reason = request.getParameter("reason");

                    returnDAO.createReturn(new ReturnDTO(0, invoiceID, reason, "Pending"));
                    request.setAttribute("successMsg", "Return request submitted.");
                    request.getRequestDispatcher("invoice.jsp").forward(request, response);
                    break;
                }

                case "HandleReturn": {
                    if (user == null || !"AD".equals(user.getRoleID())) {
                        response.sendRedirect("login.jsp");
                        return;
                    }

                    int returnID = Integer.parseInt(request.getParameter("returnID"));
                    String status = request.getParameter("status");

                    returnDAO.updateReturnStatus(returnID, status);
                    request.setAttribute("successMsg", "Return status updated.");
                    request.getRequestDispatcher("MainController?action=ListReturns").forward(request, response);
                    break;
                }

                case "ListReturns": {
                    if (user == null || !"AD".equals(user.getRoleID())) {
                        response.sendRedirect("login.jsp");
                        return;
                    }

                    List<ReturnDTO> list = returnDAO.getAllReturns();
                    request.setAttribute("RETURN_LIST", list);
                    request.getRequestDispatcher("admin/view-returns.jsp").forward(request, response);
                    break;
                }

                default:
                    request.setAttribute("ERROR", "Unknown action: " + action);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Return operation failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
