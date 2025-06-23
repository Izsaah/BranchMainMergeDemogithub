package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/MainController")
public class MainController extends HttpServlet {

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
        String controller = "login.jsp"; // default fallback page

        try {
            if (action == null || action.trim().isEmpty()) {
                controller = "login.jsp";
            } else {
                switch (action) {
                    // User Actions
                    case "Login":
                    case "Logout":
                    case "AddUser":
                    case "ListUsers":
                    case "UpdateUser":
                    case "DeleteUser":
                        controller = "UserController?action=" + action;
                        break;

                    // Product Actions
                    case "AddProduct":
                    case "ListProducts":
                    case "UpdateProduct":
                    case "DeleteProduct":
                        controller = "ProductController?action=" + action;
                        break;

                    // Category Actions
                    case "AddCategory":
                    case "ListCategories":
                        controller = "CategoryController?action=" + action;
                        break;

                    // Promotion
                    case "AddPromotion":
                        controller = "PromotionController?action=AddPromotion";
                        break;

                    // Cart
                    case "ViewCart":
                        controller = "CartController?action=ViewCart";
                        break;

                    // Checkout & Invoice
                    case "Checkout":
                        controller = "InvoiceController?action=Checkout";
                        break;

                    // Delivery
                    case "TrackDelivery":
                        controller = "DeliveryController?action=TrackDelivery";
                        break;

                    // Returns
                    case "SubmitReturn":
                        controller = "ReturnController?action=SubmitReturn";
                        break;

                    // Customer Support
                    case "CustomerSupport":
                        controller = "CustomerCareController?action=CustomerSupport";
                        break;

                    default:
                        controller = "error.jsp";
                        request.setAttribute("ERROR", "Unknown action: " + action);
                        break;
                }
            }

            request.getRequestDispatcher(controller).forward(request, response);

        } catch (Exception e) {
            request.setAttribute("ERROR", "Something went wrong: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
