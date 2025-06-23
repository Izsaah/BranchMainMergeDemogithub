package controller;

import dao.InvoiceDetailsDAO;
import dto.InvoiceDetailsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InvoiceDetailsController", urlPatterns = {"/InvoiceDetailsController"})
public class InvoiceDetailsController extends HttpServlet {

    // Page constants
    public static final String ERROR_PAGE = "error.jsp";
    public static final String INVOICE_DETAIL_PAGE = "invoiceDetails.jsp";
    public static final String LIST_DETAILS_PAGE = "listInvoiceDetails.jsp";

    private static final InvoiceDetailsDAO IDAO = new InvoiceDetailsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Invoice Details Controller";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String url = ERROR_PAGE;

        try {
            String action = request.getParameter("action");

            if ("ViewInvoiceDetails".equals(action)) {
                url = handleViewInvoiceDetails(request, response);
            } else if ("ListInvoiceDetails".equals(action)) {
                url = handleListAllDetails(request, response);
            } else {
                request.setAttribute("errorMsg", "Invalid action: " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Controller error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String handleViewInvoiceDetails(HttpServletRequest request, HttpServletResponse response) {
        int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
        List<InvoiceDetailsDTO> details = IDAO.getInvoiceDetailsByInvoiceID(invoiceID);
        if (details == null || details.isEmpty()) {
            request.setAttribute("errorMsg", "No details found for this invoice.");
            return ERROR_PAGE;
        }

        request.setAttribute("INVOICE_ID", invoiceID);
        request.setAttribute("DETAIL_LIST", details);
        return INVOICE_DETAIL_PAGE;
    }

    private String handleListAllDetails(HttpServletRequest request, HttpServletResponse response) {
        List<InvoiceDetailsDTO> list = IDAO.getAllInvoiceDetails();
        request.setAttribute("DETAIL_LIST", list);
        return LIST_DETAILS_PAGE;
    }
}
