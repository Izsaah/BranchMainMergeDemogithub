package controller;

import dao.ReturnsDAO;
import dto.ReturnsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReturnController", urlPatterns = {"/ReturnController"})
public class ReturnsController extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String RETURN_FORM_PAGE = "return-form.jsp";
    private static final String RETURN_LIST_PAGE = "return-list.jsp";

    private static final ReturnsDAO RDAO = new ReturnsDAO();

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
                case "SubmitReturn":
                    url = handleSubmitReturn(request);
                    break;
                case "ListReturns":
                    url = handleListReturns(request);
                    break;
                case "ToEditReturn":
                    url = handleToEditReturn(request);
                    break;
                case "UpdateReturn":
                    url = handleUpdateReturn(request);
                    break;
                case "DeleteReturn":
                    url = handleDeleteReturn(request);
                    break;
                default:
                    request.setAttribute("errorMsg", "Invalid action: " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Controller error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String handleSubmitReturn(HttpServletRequest request) {
        try {
            int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
            String reason = request.getParameter("reason");
            String status = request.getParameter("status");

            ReturnsDTO returnDTO = new ReturnsDTO(0, invoiceID, reason, status);
            boolean success = RDAO.addReturn(returnDTO);

            if (!success) {
                request.setAttribute("errorMsg", "Failed to submit return.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid input for SubmitReturn.");
            return ERROR_PAGE;
        }

        return handleListReturns(request);
    }

    private String handleListReturns(HttpServletRequest request) {
        List<ReturnsDTO> list = RDAO.getAllReturns();
        request.setAttribute("RETURN_LIST", list);
        return RETURN_LIST_PAGE;
    }

    private String handleToEditReturn(HttpServletRequest request) {
        try {
            int returnID = Integer.parseInt(request.getParameter("returnID"));
            ReturnsDTO dto = RDAO.getReturnByID(returnID);

            if (dto == null) {
                request.setAttribute("errorMsg", "Return request not found.");
                return ERROR_PAGE;
            }

            request.setAttribute("RETURN", dto);
            request.setAttribute("actionType", "UpdateReturn");
            return RETURN_FORM_PAGE;

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid return ID.");
            return ERROR_PAGE;
        }
    }

    private String handleUpdateReturn(HttpServletRequest request) {
        try {
            int returnID = Integer.parseInt(request.getParameter("returnID"));
            int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
            String reason = request.getParameter("reason");
            String status = request.getParameter("status");

            ReturnsDTO dto = new ReturnsDTO(returnID, invoiceID, reason, status);
            boolean updated = RDAO.updateReturnStatus(returnID, status);

            if (!updated) {
                request.setAttribute("errorMsg", "Failed to update return.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid input for UpdateReturn.");
            return ERROR_PAGE;
        }

        return handleListReturns(request);
    }

    private String handleDeleteReturn(HttpServletRequest request) {
        try {
            int returnID = Integer.parseInt(request.getParameter("returnID"));
            boolean deleted = RDAO.deleteReturn(returnID);

            if (!deleted) {
                request.setAttribute("errorMsg", "Failed to delete return.");
                return ERROR_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Invalid return ID for Delete.");
            return ERROR_PAGE;
        }

        return handleListReturns(request);
    }
}
