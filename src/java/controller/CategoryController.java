package controller;

import dao.CategoriesDAO;
import dto.CategoriesDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {

    private final CategoriesDAO dao = new CategoriesDAO();
    private static final String CATEGORY_LIST = "admin/category-list.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = ERROR_PAGE;

        try {
            switch (action) {
                case "ListCategories":
                    url = listCategories(request);
                    break;
                case "AddCategory":
                    url = addCategory(request);
                    break;
                case "UpdateCategory":
                    url = updateCategory(request);
                    break;
                case "DeleteCategory":
                    url = deleteCategory(request);
                    break;
                case "SearchCategories":
                    url = searchCategories(request);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", "Category error: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String listCategories(HttpServletRequest request) {
        List<CategoriesDTO> list = dao.getAllCategories();
        request.setAttribute("CATEGORY_LIST", list);
        return CATEGORY_LIST;
    }

    private String addCategory(HttpServletRequest request) {
        String name = request.getParameter("categoryName");
        String desc = request.getParameter("description");
        dao.addCategory(new CategoriesDTO(0, name, desc));
        return listCategories(request);
    }

    private String updateCategory(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("categoryID"));
        String name = request.getParameter("categoryName");
        String desc = request.getParameter("description");
        dao.updateCategory(new CategoriesDTO(id, name, desc));
        return listCategories(request);
    }

    private String deleteCategory(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("categoryID"));
        dao.deleteCategory(id);
        return listCategories(request);
    }

    private String searchCategories(HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        List<CategoriesDTO> result = dao.searchCategories(keyword);
        request.setAttribute("CATEGORY_LIST", result);
        return CATEGORY_LIST;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        processRequest(req, resp);
    }
}
