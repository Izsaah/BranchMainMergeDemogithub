package controller;

import dao.CartsDAO;
import dto.CartsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CartsController")
public class CartsController extends HttpServlet {

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

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        CartsDAO dao = new CartsDAO();

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("ERROR", "Action is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            switch (action.trim()) {

                case "CreateCart": {
                    String userID = request.getParameter("userID");

                    // You can set the date on the server side
                    CartsDTO cart = new CartsDTO();
                    cart.setUserID(userID);
                    cart.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));

                    int created = dao.createCart(cart);
                    if (created>0) {
                        response.sendRedirect("MainController?action=ViewCart&userID=" + userID);
                    } else {
                        request.setAttribute("ERROR", "Failed to create cart.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                }

                case "ViewCart": {
                    String userID = request.getParameter("userID");
                    CartsDTO cart = dao.getLatestCartByUser(userID);

                    if (cart != null) {
                        request.setAttribute("CART", cart);
                        request.getRequestDispatcher("cart.jsp").forward(request, response);
                    } else {
                        request.setAttribute("ERROR", "Cart not found.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                }

                case "ListCarts": {
                    List<CartsDTO> list = dao.getAllCarts();
                    request.setAttribute("CART_LIST", list);
                    request.getRequestDispatcher("listCarts.jsp").forward(request, response);
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
            request.setAttribute("ERROR", "Cart controller failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
