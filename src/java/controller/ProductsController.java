package controller;

import dao.ProductsDAO;
import dto.ProductsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductsController extends HttpServlet {

    // Page constants
    public static final String ERROR_PAGE = "error.jsp";
    public static final String PRODUCT_FORM_PAGE = "product-form.jsp";
    public static final String PRODUCT_LIST_PAGE = "product-list.jsp";

    private static final ProductsDAO PDAO = new ProductsDAO();

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
        return "Products Controller";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String url = ERROR_PAGE;

        try {
            String action = request.getParameter("action");

            switch (action) {
                case "AddProduct":
                    url = handleAddProduct(request);
                    break;
                case "ListProducts":
                    url = handleListProducts(request);
                    break;
                case "ToEditProduct":
                    url = handleToEditProduct(request);
                    break;
                case "UpdateProduct":
                    url = handleUpdateProduct(request);
                    break;
                case "DeleteProduct":
                    url = handleDeleteProduct(request);
                    break;
                default:
                    request.setAttribute("errorMsg", "Invalid action: " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Controller error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String handleAddProduct(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            float price = Float.parseFloat(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String sellerID = request.getParameter("sellerID");
            String status = request.getParameter("status");

            ProductsDTO product = new ProductsDTO(0, name, categoryID, price, quantity, sellerID, status);
            boolean added = PDAO.addProduct(product);

            if (!added) {
                request.setAttribute("errorMsg", "Failed to add product.");
                return ERROR_PAGE;
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid input for AddProduct.");
            return ERROR_PAGE;
        }

        return handleListProducts(request);
    }

    private String handleListProducts(HttpServletRequest request) {
        List<ProductsDTO> list = PDAO.getAllProducts();
        request.setAttribute("PRODUCT_LIST", list);
        return PRODUCT_LIST_PAGE;
    }

    private String handleToEditProduct(HttpServletRequest request) {
        try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            ProductsDTO product = PDAO.getProductByID(productID);

            if (product == null) {
                request.setAttribute("errorMsg", "Product not found.");
                return ERROR_PAGE;
            }

            request.setAttribute("PRODUCT", product);
            request.setAttribute("actionType", "UpdateProduct");
            return PRODUCT_FORM_PAGE;

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid product ID.");
            return ERROR_PAGE;
        }
    }

    private String handleUpdateProduct(HttpServletRequest request) {
        try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            String name = request.getParameter("name");
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            float price = Float.parseFloat(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String sellerID = request.getParameter("sellerID");
            String status = request.getParameter("status");

            ProductsDTO updated = new ProductsDTO(productID, name, categoryID, price, quantity, sellerID, status);
            boolean updatedSuccess = PDAO.updateProduct(updated);

            if (!updatedSuccess) {
                request.setAttribute("errorMsg", "Failed to update product.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid input for UpdateProduct.");
            return ERROR_PAGE;
        }

        return handleListProducts(request);
    }

    private String handleDeleteProduct(HttpServletRequest request) {
        try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            boolean deleted = PDAO.deleteProduct(productID);

            if (!deleted) {
                request.setAttribute("errorMsg", "Failed to delete product.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid product ID for Delete.");
            return ERROR_PAGE;
        }

        return handleListProducts(request);
    }
}
