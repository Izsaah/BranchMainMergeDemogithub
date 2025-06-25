package controller;

import dao.CartDAO;
import dao.CartDetailsDAO;
import dao.InvoiceDAO;
import dao.InvoiceDetailsDAO;
import dao.ProductsDAO;
import dto.CartDetailsDTO;
import dto.CartItemDTO;
import dto.InvoiceDetailsDTO;
import dto.ProductsDTO;
import dto.UsersDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {
    private static final CartDAO cartDAO = new CartDAO();
    private static final CartDetailsDAO cartDetailsDAO = new CartDetailsDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handle(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handle(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";

        try {
            switch (action) {
                case "AddToCart":
                    url = addToCart(request);
                    break;
                case "ViewCart":
                    url = viewCart(request);
                    break;
                case "UpdateCart":
                    url = updateCart(request);
                    break;
                case "RemoveFromCart":
                    url = removeFromCart(request);
                    break;
                case "Checkout":
                    url = checkout(request);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String addToCart(HttpServletRequest req) throws Exception {
        UsersDTO user = (UsersDTO) req.getSession().getAttribute("LOGIN_USER");
        if (user == null) return "login.jsp";

        int productID = Integer.parseInt(req.getParameter("productID"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        int cartID = cartDAO.createCartIfNotExists(user.getUserID());
        cartDetailsDAO.addOrUpdateItem(cartID, productID, quantity);

        return viewCart(req);
    }

private String viewCart(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession();
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null) return "login.jsp";

    int cartID = cartDAO.createCartIfNotExists(user.getUserID());

    List<CartDetailsDTO> details = cartDetailsDAO.getCartItems(cartID);
    ProductsDAO productsDAO = new ProductsDAO();

    List<CartItemDTO> items = new ArrayList<>();
    for (CartDetailsDTO detail : details) {
        ProductsDTO product = productsDAO.getProductByID(detail.getProductID());
        if (product != null) {
            items.add(new CartItemDTO(
                product.getProductID(),
                product.getName(),
                product.getPrice(),
                detail.getQuantity()
            ));
        }
    }

    request.setAttribute("CART_ITEMS", items);
    return "cart.jsp";
}


    private String updateCart(HttpServletRequest req) throws Exception {
        UsersDTO user = (UsersDTO) req.getSession().getAttribute("LOGIN_USER");
        if (user == null) return "login.jsp";

        int cartID = cartDAO.getCartIDByUser(user.getUserID());
        int productID = Integer.parseInt(req.getParameter("productID"));
        int newQuantity = Integer.parseInt(req.getParameter("quantity"));

        cartDetailsDAO.updateItemQuantity(cartID, productID, newQuantity);
        return viewCart(req);
    }

    private String removeFromCart(HttpServletRequest req) throws Exception {
        UsersDTO user = (UsersDTO) req.getSession().getAttribute("LOGIN_USER");
        if (user == null) return "login.jsp";

        int cartID = cartDAO.getCartIDByUser(user.getUserID());
        int productID = Integer.parseInt(req.getParameter("productID"));

        cartDetailsDAO.removeItem(cartID, productID);
        return viewCart(req);
    }

 private String checkout(HttpServletRequest req) throws Exception {
    UsersDTO user = (UsersDTO) req.getSession().getAttribute("LOGIN_USER");
    if (user == null) return "login.jsp";

    int cartID = cartDAO.getCartIDByUser(user.getUserID());
    List<CartDetailsDTO> cartItems = cartDetailsDAO.getCartItems(cartID);

    if (cartItems.isEmpty()) {
        req.setAttribute("errorMsg", "Your cart is empty.");
        return viewCart(req);
    }

    ProductsDAO productsDAO = new ProductsDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    InvoiceDetailsDAO detailsDAO = new InvoiceDetailsDAO();

    float total = 0;
    for (CartDetailsDTO item : cartItems) {
        ProductsDTO product = productsDAO.getProductByID(item.getProductID());
        if (product != null) {
            total += product.getPrice() * item.getQuantity();
        }
    }

    // Create invoice
    int invoiceID = invoiceDAO.createInvoice(user.getUserID(), total);

    // Add invoice details
    for (CartDetailsDTO item : cartItems) {
        ProductsDTO product = productsDAO.getProductByID(item.getProductID());
        if (product != null) {
            detailsDAO.addInvoiceDetail(invoiceID, item.getProductID(), item.getQuantity(), product.getPrice());
        }
    }

    // Clear cart
    cartDetailsDAO.clearCart(cartID);

    req.setAttribute("successMsg", "Checkout successful! Invoice #" + invoiceID);
    return "MainController?action=ViewInvoices";
}

    
}