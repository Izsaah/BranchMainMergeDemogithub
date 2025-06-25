package filters;

import dto.UsersDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(filterName = "RoleFilter", urlPatterns = {"/admin/*"})
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        UsersDTO user = (session != null) ? (UsersDTO) session.getAttribute("LOGIN_USER") : null;

        if (user == null || !"AD".equals(user.getRoleID())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(req, res);
    }
}
