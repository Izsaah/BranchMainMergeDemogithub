package controller;

import dao.UserDAO;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        switch (action) {
            case "Login":
                res.sendRedirect("login.jsp");
                break;

            case "Logout":
                req.getSession().invalidate();
                res.sendRedirect("login.jsp");
                break;

            case "ListUsers":
                try {
                    List<UserDTO> list = new UserDAO().getAllUsers();
                    req.setAttribute("USER_LIST", list);
                    req.getRequestDispatcher("userList.jsp").forward(req, res);
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    res.sendError(500, "Internal Server Error");
                }
                break;

            default:
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        switch (action) {
            case "Login":
                String userID = req.getParameter("userID").trim();
                String password = req.getParameter("password").trim();

                try {
                    UserDTO user = new UserDAO().login(userID, password);
                    if (user != null) {
                        req.getSession().setAttribute("LOGIN_USER", user);
                        res.sendRedirect("welcome.jsp");
                    } else {
                        req.setAttribute("ERROR", "Sai tài khoản hoặc mật khẩu.");
                        req.getRequestDispatcher("login.jsp").forward(req, res);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    req.setAttribute("ERROR", "Lỗi đăng nhập.");
                    req.getRequestDispatcher("login.jsp").forward(req, res);
                }
                break;

            case "AddUser":
                try {
                    String newUserID = req.getParameter("userID").trim();
                    String fullName = req.getParameter("fullName").trim();
                    String roleID = req.getParameter("roleID").trim();
                    String newPassword = req.getParameter("password").trim();
                    String phone = req.getParameter("phone").trim();

                    UserDTO newUser = new UserDTO(newUserID, fullName, roleID, newPassword, phone);
                    boolean ok = new UserDAO().create(newUser);

                    if (ok) {
                        res.sendRedirect("UserController?action=ListUsers");
                    } else {
                        req.setAttribute("ERROR", "Thêm user thất bại");
                        req.getRequestDispatcher("addUser.jsp").forward(req, res);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    req.setAttribute("ERROR", "Lỗi server khi thêm user.");
                    req.getRequestDispatcher("addUser.jsp").forward(req, res);
                }
                break;

            default:
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
        }
    }
}
