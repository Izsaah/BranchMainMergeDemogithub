package controller;

import dao.CartDetailsDAO;
import dao.ProductsDAO;
import dto.CartDetailsDTO;
import dto.ProductsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CartDetailsController")
public class CartDetailsController extends HttpServlet {

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
        CartDetailsDAO dao = new CartDetailsDAO();

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("ERROR", "Action is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            switch (action.trim()) {
                case "AddToCart": {
                    int cartID = Integer.parseInt(request.getParameter("cartID"));
                    int productID = Integer.parseInt(request.getParameter("productID"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    ProductsDTO product = new ProductsDAO().getProductByID(productID);
                    if (product != null && product.getQuantity() >= quantity) {
                        CartDetailsDTO detail = new CartDetailsDTO(cartID, productID, quantity);
                        dao.addCartDetail(detail);
                        response.sendRedirect("MainController?action=ViewCart");
                    } else {
                        request.setAttribute("ERROR", "Invalid product or insufficient quantity.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                }

                case "RemoveFromCart": {
                    int cartID = Integer.parseInt(request.getParameter("cartID"));
                    int productID = Integer.parseInt(request.getParameter("productID"));

                    dao.deleteCartDetail(cartID, productID);
                    response.sendRedirect("MainController?action=ViewCart");
                    break;
                }

                case "UpdateCartItem": {
                    int cartID = Integer.parseInt(request.getParameter("cartID"));
                    int productID = Integer.parseInt(request.getParameter("productID"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    CartDetailsDTO cartDetail=new CartDetailsDTO(cartID, productID, quantity);
                    dao.updateCartDetail(cartDetail);
                    response.sendRedirect("MainController?action=ViewCart");
                    break;
                }

                case "ViewCartItems": {
                    int cartID = Integer.parseInt(request.getParameter("cartID"));

                    List<CartDetailsDTO> list = dao.getCartDetailsByCartID(cartID);
                    request.setAttribute("CART_ITEMS", list);
                    request.getRequestDispatcher("cartDetails.jsp").forward(request, response);
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
            request.setAttribute("ERROR", "Cart operation failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
