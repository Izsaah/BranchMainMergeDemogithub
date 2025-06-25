<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.UsersDTO" %>
<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Promotion List</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>

    <h2>Promotion Management - <c:out value="${user.fullName}" /></h2>

    <!-- 🔍 Search Form -->
    <form action="MainController" method="get" style="text-align: center;">
        <input type="hidden" name="action" value="SearchPromotions"/>
        <input type="text" name="keyword" placeholder="Search by promotion name" value="${param.keyword}" />
        <input type="submit" value="Search" />
    </form>

    <!-- 📋 Promotion Table -->
    <c:if test="${not empty PROMOTION_LIST}">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Discount (%)</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="promo" items="${PROMOTION_LIST}">
                <tr>
                    <td>${promo.promoID}</td>
                    <td>${promo.name}</td>
                    <td>${promo.discountPercent}</td>
                    <td>${promo.startDate}</td>
                    <td>${promo.endDate}</td>
                    <td>${promo.status}</td>
                    <td>
                        <a class="button" href="MainController?action=EditPromotion&promoID=${promo.promoID}">Edit</a>
                        <form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="DeletePromotion" />
                            <input type="hidden" name="promoID" value="${promo.promoID}" />
                            <button type="submit" class="button red" onclick="return confirm('Delete this promotion?');">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!-- ❌ No Result -->
    <c:if test="${empty PROMOTION_LIST}">
        <p style="text-align: center;">No promotions found.</p>
    </c:if>

    <!-- ➕ Add New -->
    <p style="text-align: center;">
        <a class="button" href="promotion-form.jsp?action=Add">Add New Promotion</a>
    </p>

    <p style="text-align: center;"><a href="admin-dashboard.jsp">Back to Admin Dashboard</a></p>

</body>
</html>
