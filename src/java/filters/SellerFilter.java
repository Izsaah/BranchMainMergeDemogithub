package filters;

import dto.UsersDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(filterName = "SellerFilter", urlPatterns = {"/product-form.jsp", "/ProductController"})
public class SellerFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        UsersDTO user = (session != null) ? (UsersDTO) session.getAttribute("LOGIN_USER") : null;

        String action = req.getParameter("action");

        // Only protect sensitive seller actions
        if (action != null && (action.equals("AddProduct") || action.equals("UpdateProduct") || action.equals("DeleteProduct"))) {
            if (user != null && (user.getRoleID().equals("SE") || user.getRoleID().equals("AD"))) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect("login.jsp");
            }
        } else {
            // For other actions (like ListProducts), allow through
            chain.doFilter(request, response);
        }
    }
}
