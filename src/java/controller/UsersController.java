package controller;

import dao.UsersDAO;
import dto.UsersDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UsersController extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String HOME_PAGE = "home.jsp";
    private static final String USER_LIST_PAGE = "user-list.jsp";

    private static final UsersDAO UDAO = new UsersDAO();

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = ERROR_PAGE;

        try {
            String action = request.getParameter("action");

            if (action == null) {
                request.setAttribute("errorMsg", "Action is required.");
            } else {
                switch (action) {
                    case "Login":
                        url = handleLogin(request);
                        break;
                    case "Logout":
                        url = handleLogout(request);
                        break;
                    case "AddUser":
                        url = handleAddUser(request);
                        break;
                    case "ListUsers":
                        url = handleListUsers(request);
                        break;
                    case "UpdateUser":
                        url = handleUpdateUser(request);
                        break;
                    case "DeleteUser":
                        url = handleDeleteUser(request);
                        break;
                    case "SearchUsers":
                        url = handleSearchUsers(request);
                        break;
                    default:
                        request.setAttribute("errorMsg", "Invalid action: " + action);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Controller error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String handleLogin(HttpServletRequest request) {
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");

        UsersDTO user = UDAO.checkLogin(userID, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_USER", user);

            // Redirect based on role
            switch (user.getRoleID()) {
                case "AD":
                    return "admin-dashboard.jsp";
                case "SE":
                    return "seller-dashboard.jsp";
                case "BU":
                    return "buyer-dashboard.jsp";
                case "MK":
                    return "marketing-dashboard.jsp";
                case "AC":
                    return "accounting-dashboard.jsp";
                case "DL":
                    return "delivery-dashboard.jsp";
                case "CS":
                    return "customer-service-dashboard.jsp";
                default:
                    request.setAttribute("errorMsg", "Unknown role.");
                    return LOGIN_PAGE;
            }
        } else {
            request.setAttribute("errorMsg", "Invalid login credentials.");
            return LOGIN_PAGE;
        }
    }

    private String handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return LOGIN_PAGE;
    }

    private String handleAddUser(HttpServletRequest request) {
        String userID = request.getParameter("userID");
        String fullName = request.getParameter("fullName");
        String roleID = request.getParameter("roleID");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        UsersDTO user = new UsersDTO(userID, fullName, roleID, password, phone);
        boolean added = UDAO.addUser(user);

        if (!added) {
            request.setAttribute("errorMsg", "Failed to add user.");
            return ERROR_PAGE;
        }

        return handleListUsers(request);
    }

    private String handleListUsers(HttpServletRequest request) {
        List<UsersDTO> list = UDAO.getAllUsers();
        request.setAttribute("USER_LIST", list);
        return USER_LIST_PAGE;
    }

    private String handleUpdateUser(HttpServletRequest request) {
        String userID = request.getParameter("userID");
        String fullName = request.getParameter("fullName");
        String roleID = request.getParameter("roleID");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        UsersDTO user = new UsersDTO(userID, fullName, roleID, password, phone);
        boolean updated = UDAO.updateUser(user);

        if (!updated) {
            request.setAttribute("errorMsg", "Failed to update user.");
            return ERROR_PAGE;
        }

        return handleListUsers(request);
    }

    private String handleDeleteUser(HttpServletRequest request) {
        String userID = request.getParameter("userID");
        boolean deleted = UDAO.deleteUser(userID);

        if (!deleted) {
            request.setAttribute("errorMsg", "Failed to delete user.");
            return ERROR_PAGE;
        }

        return handleListUsers(request);
    }

    private String handleSearchUsers(HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        List<UsersDTO> result = UDAO.searchUsers(keyword);
        request.setAttribute("USER_LIST", result);
        return USER_LIST_PAGE;
    }

}
