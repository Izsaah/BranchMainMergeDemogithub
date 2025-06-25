package controller;

import dao.CategoriesDAO;
import dao.ProductsDAO;
import dto.CategoriesDTO;
import dto.ProductsDTO;
import dto.UsersDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {
    private static final ProductsDAO productDAO = new ProductsDAO();
    private static final CategoriesDAO categoryDAO = new CategoriesDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";

        try {
            switch (action) {
                case "ListProducts":
                    url = listProducts(request);
                    break;
                case "ShowProductForm":
                    url = showProductForm(request);
                    break;
                case "AddProduct":
                    url = addProduct(request);
                    break;
                case "UpdateProduct":
                    url = updateProduct(request);
                    break;
                case "DeleteProduct":
                    url = deleteProduct(request);
                    break;
                case "SearchProduct":
                    url=searchProducts(request);
                    break;
                default:
                    request.setAttribute("errorMsg", "Unknown product action: " + action);
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String listProducts(HttpServletRequest req) throws Exception {
        UsersDTO user = (UsersDTO) req.getSession().getAttribute("LOGIN_USER");
        List<ProductsDTO> products = user.getRoleID().equals("AD")
                ? productDAO.getAllProducts()
                : productDAO.getProductsBySeller(user.getUserID());

        req.setAttribute("PRODUCT_LIST", products);
        return "product-list.jsp";
    }

    private String showProductForm(HttpServletRequest req) throws Exception {
        List<CategoriesDTO> categories = categoryDAO.getAllCategories();
        req.setAttribute("CATEGORY_LIST", categories);

        String pid = req.getParameter("productID");
        if (pid != null && !pid.isEmpty()) {
            int productID = Integer.parseInt(pid);
            ProductsDTO product = productDAO.getProductByID(productID);
            req.setAttribute("PRODUCT", product);
        }

        return "product-form.jsp";
    }

    private String addProduct(HttpServletRequest req) throws Exception {
        String name = req.getParameter("name");
        int categoryID = Integer.parseInt(req.getParameter("categoryID"));
        float price = Float.parseFloat(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String status = req.getParameter("status");
        String sellerID = ((UsersDTO) req.getSession().getAttribute("LOGIN_USER")).getUserID();

        if (quantity < 0 || (!status.equalsIgnoreCase("active") && !status.equalsIgnoreCase("inactive"))) {
            req.setAttribute("errorMsg", "Invalid quantity or status");
            return showProductForm(req);
        }

        ProductsDTO p = new ProductsDTO(0, name, categoryID, price, quantity, sellerID, status);
        boolean added = productDAO.addProduct(p);

        if (!added) {
            req.setAttribute("errorMsg", "Failed to add product");
            return showProductForm(req);
        }

        return listProducts(req);
    }

    private String updateProduct(HttpServletRequest req) throws Exception {
        int id = Integer.parseInt(req.getParameter("productID"));
        String name = req.getParameter("name");
        int categoryID = Integer.parseInt(req.getParameter("categoryID"));
        float price = Float.parseFloat(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String status = req.getParameter("status");
        String sellerID = ((UsersDTO) req.getSession().getAttribute("LOGIN_USER")).getUserID();

        ProductsDTO p = new ProductsDTO(id, name, categoryID, price, quantity, sellerID, status);
        boolean updated = productDAO.updateProduct(p);

        if (!updated) {
            req.setAttribute("errorMsg", "Failed to update product");
            return showProductForm(req);
        }

        return listProducts(req);
    }

    private String deleteProduct(HttpServletRequest req) throws Exception {
        int id = Integer.parseInt(req.getParameter("productID"));
        productDAO.deleteProduct(id);
        return listProducts(req);
    }
    private String searchProducts(HttpServletRequest req) throws Exception {
        
    String keyword = req.getParameter("keyword");
    String catParam = req.getParameter("categoryID");
    String status = req.getParameter("status");
    String min = req.getParameter("minPrice");
    String max = req.getParameter("maxPrice");

    Integer categoryID = (catParam != null && !catParam.isEmpty()) ? Integer.parseInt(catParam) : null;
    Float minPrice = (min != null && !min.isEmpty()) ? Float.parseFloat(min) : null;
    Float maxPrice = (max != null && !max.isEmpty()) ? Float.parseFloat(max) : null;

    List<ProductsDTO> results = productDAO.searchProducts(keyword, categoryID, status, minPrice, maxPrice);
    List<CategoriesDTO> categories = categoryDAO.getAllCategories();

    req.setAttribute("PRODUCT_LIST", results);
    req.setAttribute("CATEGORY_LIST", categories);
    req.setAttribute("keyword", keyword);
    req.setAttribute("categoryID", categoryID);
    req.setAttribute("status", status);
    req.setAttribute("minPrice", minPrice);
    req.setAttribute("maxPrice", maxPrice);

    return "product-list.jsp";
}

}
