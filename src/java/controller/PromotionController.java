package controller;

import dao.PromotionsDAO;
import dto.PromotionsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/PromotionController")
public class PromotionController extends HttpServlet {
    private final PromotionsDAO dao = new PromotionsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            switch (action) {
                case "ListPromotions":
                    listPromotions(request, response);
                    break;

                case "SearchPromotions":
                    searchPromotions(request, response);
                    break;

                case "EditPromotion":
                    showEditForm(request, response);
                    break;

                default:
                    request.setAttribute("ERROR", "Invalid GET action: " + action);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            switch (action) {
                case "AddPromotion":
                    addPromotion(request, response);
                    break;

                case "UpdatePromotion":
                    updatePromotion(request, response);
                    break;

                case "DeletePromotion":
                    deletePromotion(request, response);
                    break;

                default:
                    request.setAttribute("ERROR", "Invalid POST action: " + action);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listPromotions(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<PromotionsDTO> list = dao.getAllPromotions();
        request.setAttribute("PROMOTION_LIST", list);
        request.getRequestDispatcher("promotion-list.jsp").forward(request, response);
    }

    private void searchPromotions(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String keyword = request.getParameter("keyword");
        List<PromotionsDTO> list = dao.searchPromotions(keyword);
        request.setAttribute("PROMOTION_LIST", list);
        request.getRequestDispatcher("promotion-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int promoID = Integer.parseInt(request.getParameter("promoID"));
        PromotionsDTO promo = dao.getPromotionByID(promoID);
        request.setAttribute("PROMOTION", promo);
        request.getRequestDispatcher("promotion-form.jsp").forward(request, response);
    }

    private void addPromotion(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PromotionsDTO dto = extractPromotionFromRequest(request);
        dao.addPromotion(dto);
        response.sendRedirect("MainController?action=ListPromotions");
    }

    private void updatePromotion(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PromotionsDTO dto = extractPromotionFromRequest(request);
        dto.setPromoID(Integer.parseInt(request.getParameter("promoID")));
        dao.updatePromotion(dto);
        response.sendRedirect("MainController?action=ListPromotions");
    }

    private void deletePromotion(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int promoID = Integer.parseInt(request.getParameter("promoID"));
        dao.deletePromotion(promoID);
        response.sendRedirect("MainController?action=ListPromotions");
    }

    private PromotionsDTO extractPromotionFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        float discount = Float.parseFloat(request.getParameter("discountPercent"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        String status = request.getParameter("status");

        return new PromotionsDTO(0, name, discount, startDate, endDate, status);
    }
}
