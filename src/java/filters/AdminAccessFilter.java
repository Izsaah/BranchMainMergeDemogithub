package filters;

import dto.UsersDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminAccessFilter", urlPatterns = {
    "/AdminDashboard.jsp",
    "/UserController", 
    "/CategoryController"
})
public class AdminAccessFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        UsersDTO user = (session != null) ? (UsersDTO) session.getAttribute("LOGIN_USER") : null;

        if (user == null || !"AD".equals(user.getRoleID())) {
            res.sendRedirect("login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {}
    public void destroy() {}
}
