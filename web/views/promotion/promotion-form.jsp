<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.PromotionsDTO, dto.UsersDTO" %>
<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }

    PromotionsDTO promo = (PromotionsDTO) request.getAttribute("PROMOTION");
    boolean isEdit = promo != null;
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= isEdit ? "Edit Promotion" : "Add Promotion" %></title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>

<h2><%= isEdit ? "Edit Promotion" : "Add New Promotion" %></h2>

<form action="MainController" method="post">
    <input type="hidden" name="action" value="<%= isEdit ? "UpdatePromotion" : "AddPromotion" %>"/>
    <c:if test="${not empty PROMOTION}">
        <input type="hidden" name="promoID" value="${PROMOTION.promoID}" />
    </c:if>

    <label for="name">Promotion Name:</label>
    <input type="text" id="name" name="name" required value="${PROMOTION.name != null ? PROMOTION.name : ''}" />

    <label for="discountPercent">Discount Percent (%):</label>
    <input type="number" step="0.01" min="0" max="100" id="discountPercent" name="discountPercent" required value="${PROMOTION.discountPercent != null ? PROMOTION.discountPercent : ''}" />

    <label for="startDate">Start Date:</label>
    <input type="date" id="startDate" name="startDate" required value="${PROMOTION.startDate != null ? PROMOTION.startDate : ''}" />

    <label for="endDate">End Date:</label>
    <input type="date" id="endDate" name="endDate" required value="${PROMOTION.endDate != null ? PROMOTION.endDate : ''}" />

    <label for="status">Status:</label>
    <select id="status" name="status" required>
        <option value="active" ${PROMOTION.status == 'active' ? 'selected' : ''}>Active</option>
        <option value="inactive" ${PROMOTION.status == 'inactive' ? 'selected' : ''}>Inactive</option>
    </select>

    <input type="submit" value="<%= isEdit ? "Update" : "Add" %> Promotion" />
</form>

<p style="text-align: center;">
    <a href="MainController?action=ListPromotions">Back to Promotion List</a>
</p>

</body>
</html>
