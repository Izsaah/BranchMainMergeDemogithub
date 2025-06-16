package controller;

import dao.UserDAO;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("Login".equals(action)) {
            // Chuyển sang doPost hoặc redirect form
        } else if ("Logout".equals(action)) {
            req.getSession().invalidate();
            res.sendRedirect("login.jsp");
        } else if ("ListUsers".equals(action)) {
            List<UserDTO> list = UserDAO.getAllUsers();
            req.setAttribute("USER_LIST", list);
            req.getRequestDispatcher("userList.jsp").forward(req, res);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("Login".equals(action)) {
            String userID = req.getParameter("userID");
            String password = req.getParameter("password");
            UserDTO user = UserDAO.checkLogin(userID, password);
            if (user != null) {
                req.getSession().setAttribute("LOGIN_USER", user);
                res.sendRedirect("welcome.jsp");
            } else {
                req.setAttribute("ERROR", "Sai tài khoản hoặc mật khẩu.");
                req.getRequestDispatcher("login.jsp").forward(req, res);
            }
        } else if ("AddUser".equals(action)) {
            String userID = req.getParameter("userID");
            String fullName = req.getParameter("fullName");
            String roleID = req.getParameter("roleID");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            UserDTO user = new UserDTO(userID, fullName, roleID, password, phone);
            boolean ok = UserDAO.insert(user);
            if (ok) res.sendRedirect("MainController?action=ListUsers");
            else {
                req.setAttribute("ERROR", "Thêm user thất bại");
                req.getRequestDispatcher("addUser.jsp").forward(req, res);
            }
        }
    }
}
