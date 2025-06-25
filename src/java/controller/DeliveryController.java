package controller;

import dao.DeliveryDAO;
import dto.DeliveryDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/DeliveryController")
public class DeliveryController extends HttpServlet {
    private final DeliveryDAO deliveryDAO = new DeliveryDAO();

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
        String action = request.getParameter("action");
        String url = "error.jsp";

        try {
            switch (action) {
                case "ListDeliveries":
                    url = listDeliveries(request);
                    break;
                case "UpdateDelivery":
                    url = updateDelivery(request);
                    break;
                default:
                    request.setAttribute("ERROR", "Unknown action: " + action);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Delivery error: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String listDeliveries(HttpServletRequest request) throws Exception {
        List<DeliveryDTO> deliveries = deliveryDAO.getAllDeliveries();
        request.setAttribute("DELIVERY_LIST", deliveries);
        return "delivery-list.jsp";
    }

    private String updateDelivery(HttpServletRequest request) throws Exception {
        int deliveryID = Integer.parseInt(request.getParameter("deliveryID"));
        String address = request.getParameter("address");
        Date deliveryDate = Date.valueOf(request.getParameter("deliveryDate"));
        String status = request.getParameter("status");

        DeliveryDTO dto = new DeliveryDTO(deliveryID, 0, address, deliveryDate, status); // invoiceID is unused here
        boolean updated = deliveryDAO.updateDelivery(dto);

        if (updated) {
            request.setAttribute("successMsg", "Delivery updated successfully!");
        } else {
            request.setAttribute("errorMsg", "Failed to update delivery.");
        }

        return listDeliveries(request);
    }
}
