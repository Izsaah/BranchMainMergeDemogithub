package controller;

import dao.CategoriesDAO;
import dto.CategoriesDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CategoryController")
public class CategoriesController extends HttpServlet {

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
        CategoriesDAO dao = new CategoriesDAO();

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("ERROR", "Action is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            switch (action.trim()) {
                case "AddCategory": {
                    String name = request.getParameter("categoryName");
                    String desc = request.getParameter("description");

                    CategoriesDTO category = new CategoriesDTO();
                    category.setCategoryName(name);
                    category.setDescription(desc);

                    boolean success = dao.addCategory(category);

                    if (success) {
                        response.sendRedirect("MainController?action=ListCategories");
                    } else {
                        request.setAttribute("ERROR", "Failed to add category.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                }

                case "ListCategories": {
                    List<CategoriesDTO> list = dao.getAllCategories();
                    request.setAttribute("CATEGORY_LIST", list);
                    request.getRequestDispatcher("listCategories.jsp").forward(request, response);
                    break;
                }

                case "DeleteCategory": {
                    int id = Integer.parseInt(request.getParameter("categoryID"));
                    boolean success = dao.deleteCategory(id);

                    if (success) {
                        response.sendRedirect("MainController?action=ListCategories");
                    } else {
                        request.setAttribute("ERROR", "Failed to delete category.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
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
            request.setAttribute("ERROR", "CategoryController error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
