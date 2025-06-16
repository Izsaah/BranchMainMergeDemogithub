package controller;

import dao.ProductDAO;
import dto.ProductDTO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ProductController")
public class ProductController extends HttpServlet {

protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    String action = req.getParameter("action");

    if ("AddProduct".equals(action)) {
        String name = req.getParameter("name");
        int categoryID = Integer.parseInt(req.getParameter("categoryID"));
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String sellerID = req.getParameter("sellerID");
        String status = req.getParameter("status");

        ProductDTO product = new ProductDTO(0, name, categoryID, price, quantity, sellerID, status);
        boolean success = ProductDAO.insert(product);

        if (success) {
            res.sendRedirect("MainController?action=ListProducts");
        } else {
            req.setAttribute("ERROR", "Không thể thêm sản phẩm.");
            req.getRequestDispatcher("addProduct.jsp").forward(req, res);
        }
    }
}

protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    String action = req.getParameter("action");

    if ("ListProducts".equals(action)) {
        req.setAttribute("PRODUCT_LIST", ProductDAO.getAll());
        req.getRequestDispatcher("productList.jsp").forward(req, res);
    }
}

}
