package controller;

import dao.DeliveriesDAO;
import dto.DeliveriesDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeliveryController", urlPatterns = {"/DeliveryController"})
public class DeliveriesController extends HttpServlet {

    // Constants for JSP pages
    public static final String ERROR_PAGE = "error.jsp";
    public static final String DELIVERY_TRACK_PAGE = "trackDelivery.jsp";
    public static final String DELIVERY_LIST_PAGE = "listDeliveries.jsp";

    private static final DeliveriesDAO DDAO = new DeliveriesDAO();

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
        return "Deliveries Controller";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String url = ERROR_PAGE;
        try {
            String action = request.getParameter("action");

            if ("TrackDelivery".equals(action)) {
                url = handleTrackDelivery(request, response);
            } else if ("ListDeliveries".equals(action)) {
                url = handleListDeliveries(request, response);
            } else if ("UpdateDeliveryStatus".equals(action)) {
                url = handleUpdateStatus(request, response);
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

    private String handleTrackDelivery(HttpServletRequest request, HttpServletResponse response) {
        int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
        DeliveriesDTO delivery = DDAO.getDeliveryByInvoiceID(invoiceID);

        if (delivery == null) {
            request.setAttribute("errorMsg", "Delivery not found.");
            return ERROR_PAGE;
        }

        request.setAttribute("DELIVERY", delivery);
        return DELIVERY_TRACK_PAGE;
    }

    private String handleListDeliveries(HttpServletRequest request, HttpServletResponse response) {
        List<DeliveriesDTO> deliveries = DDAO.getAllDeliveries();
        request.setAttribute("DELIVERY_LIST", deliveries);
        return DELIVERY_LIST_PAGE;
    }

    private String handleUpdateStatus(HttpServletRequest request, HttpServletResponse response) {
        int deliveryID = Integer.parseInt(request.getParameter("deliveryID"));
        String status = request.getParameter("status");

        boolean success = DDAO.updateDeliveryStatus(deliveryID, status);
        if (!success) {
            request.setAttribute("errorMsg", "Failed to update delivery status.");
            return ERROR_PAGE;
        }

        // Optional: redirect to list or track page
        request.setAttribute("message", "Status updated successfully.");
        return handleListDeliveries(request, response);
    }
}
