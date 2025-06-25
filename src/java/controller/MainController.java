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

                    // 🔐 User Actions
                    case "Login":
                    case "Logout":
                    case "AddUser":
                    case "ListUsers":
                    case "UpdateUser":
                    case "DeleteUser":
                        controller = "UserController?action=" + action;
                        break;
                    // 🛍 Product Actions
                    case "AddProduct":
                    case "ListProducts":
                    case "UpdateProduct":
                    case "DeleteProduct":
                    case "ShowProductForm":
                    case "SearchProducts":
                        controller = "ProductController?action=" + action;
                        break;

                    // 📂 Category
                    case "ListCategories":
                    case "AddCategory":
                    case "UpdateCategory":
                    case "DeleteCategory":
                        controller = "CategoryController?action=" + action;
                        break;
                    //promotion
                    case "ListPromotions":
                    case "AddPromotion":
                    case "UpdatePromotion":
                    case "DeletePromotion":
                    case "EditPromotion":
                    case "SearchPromotions":
                        controller = "PromotionController?action=" + action;
                        break;

                    // 🛒 Cart
                    case "AddToCart":
                    case "ViewCart":
                    case "UpdateCart":
                    case "RemoveFromCart":
                    case "Checkout":
                        controller = "CartController?action=" + action;
                        break;
                    // 🧾 Invoices
                    case "ListInvoices":
                    case "ViewInvoice":
                        controller = "InvoiceController?action=" + action;
                        break;

                    // 🚚 Delivery
                    case "ListDeliveries":
                    case "UpdateDelivery":
                        controller = "DeliveryController?action=" + action;
                        break;

                    // 🔁 Returns
                    case "RequestReturn":
                    case "HandleReturn":
                        controller = "ReturnController?action=" + action;
                        break;

                    // 🎧 Customer Care
                    case "SendTicket":
                    case "ReplyTicket":
                        controller = "CustomerCareController?action=" + action;
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
