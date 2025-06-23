package controller;

import dao.PromotionsDAO;
import dto.PromotionsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "PromotionController", urlPatterns = {"/PromotionController"})
public class PromotionsController extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String PROMOTION_FORM_PAGE = "promotion-form.jsp";
    private static final String PROMOTION_LIST_PAGE = "promotion-list.jsp";

    private static final PromotionsDAO PDAO = new PromotionsDAO();

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

            switch (action) {
                case "AddPromotion":
                    url = handleAddPromotion(request);
                    break;
                case "ListPromotions":
                    url = handleListPromotions(request);
                    break;
                case "ToEditPromotion":
                    url = handleToEditPromotion(request);
                    break;
                case "UpdatePromotion":
                    url = handleUpdatePromotion(request);
                    break;
                case "DeletePromotion":
                    url = handleDeletePromotion(request);
                    break;
                default:
                    request.setAttribute("errorMsg", "Unknown action: " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String handleAddPromotion(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            float discountPercent = Float.parseFloat(request.getParameter("discountPercent"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            String status = request.getParameter("status");

            PromotionsDTO promo = new PromotionsDTO(0, name, discountPercent, startDate, endDate, status);
            boolean added = PDAO.addPromotion(promo);

            if (!added) {
                request.setAttribute("errorMsg", "Failed to add promotion.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid input for AddPromotion.");
            return ERROR_PAGE;
        }

        return handleListPromotions(request);
    }

    private String handleListPromotions(HttpServletRequest request) {
        List<PromotionsDTO> list = PDAO.getAllPromotions();
        request.setAttribute("PROMOTION_LIST", list);
        return PROMOTION_LIST_PAGE;
    }

    private String handleToEditPromotion(HttpServletRequest request) {
        try {
            int promoID = Integer.parseInt(request.getParameter("promoID"));
            PromotionsDTO promo = PDAO.getPromotionById(promoID);

            if (promo == null) {
                request.setAttribute("errorMsg", "Promotion not found.");
                return ERROR_PAGE;
            }

            request.setAttribute("PROMOTION", promo);
            request.setAttribute("actionType", "UpdatePromotion");
            return PROMOTION_FORM_PAGE;

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid promo ID.");
            return ERROR_PAGE;
        }
    }

    private String handleUpdatePromotion(HttpServletRequest request) {
        try {
            int promoID = Integer.parseInt(request.getParameter("promoID"));
            String name = request.getParameter("name");
            float discountPercent = Float.parseFloat(request.getParameter("discountPercent"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            String status = request.getParameter("status");

            PromotionsDTO promo = new PromotionsDTO(promoID, name, discountPercent, startDate, endDate, status);
            boolean updated = PDAO.updatePromotion(promo);

            if (!updated) {
                request.setAttribute("errorMsg", "Failed to update promotion.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid input for UpdatePromotion.");
            return ERROR_PAGE;
        }

        return handleListPromotions(request);
    }

    private String handleDeletePromotion(HttpServletRequest request) {
        try {
            int promoID = Integer.parseInt(request.getParameter("promoID"));
            boolean deleted = PDAO.deletePromotion(promoID);

            if (!deleted) {
                request.setAttribute("errorMsg", "Failed to delete promotion.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid promo ID for Delete.");
            return ERROR_PAGE;
        }

        return handleListPromotions(request);
    }
}
