package controller;

import dao.InvoiceDAO;
import dao.InvoiceDetailsDAO;
import dto.InvoiceDTO;
import dto.InvoiceDetailsDTO;
import dto.UsersDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InvoiceController", urlPatterns = {"/InvoiceController"})
public class InvoiceController extends HttpServlet {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceDetailsDAO invoiceDetailsDAO = new InvoiceDetailsDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = "error.jsp";

        try {
            switch (action) {
                case "ListInvoices":
                    url = listInvoices(request);
                    break;

                case "ViewInvoice":
                    url = viewInvoice(request);
                    break;

                default:
                    request.setAttribute("errorMsg", "Unknown action: " + action);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String listInvoices(HttpServletRequest request) throws Exception {
        UsersDTO user = (UsersDTO) request.getSession().getAttribute("LOGIN_USER");
        if (user == null || (!"AD".equals(user.getRoleID()) && !"AC".equals(user.getRoleID()))) {
            return "login.jsp";
        }

        List<InvoiceDTO> invoices = invoiceDAO.getAllInvoices();
        request.setAttribute("INVOICE_LIST", invoices);
        return "invoice-list.jsp";
    }

    private String viewInvoice(HttpServletRequest request) throws Exception {
        String invoiceIDStr = request.getParameter("invoiceID");
        if (invoiceIDStr == null) return "error.jsp";

        int invoiceID = Integer.parseInt(invoiceIDStr);
        InvoiceDTO invoice = invoiceDAO.getInvoiceByID(invoiceID);
        List<InvoiceDetailsDTO> details = invoiceDetailsDAO.getInvoiceDetails(invoiceID);

        request.setAttribute("INVOICE", invoice);
        request.setAttribute("INVOICE_DETAILS", details);
        return "invoice-detail.jsp";
    }
}
