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
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

    <h2>Welcome Admin: <c:out value="${user.fullName}" /></h2>

    <ul style="list-style: none; text-align: center; padding: 0;">
        <li><a class="button" href="MainController?action=ListUsers">👤 Manage Users</a></li>
        <li><a class="button" href="MainController?action=ListCategories">📁 Manage Categories</a></li>
        <li><a class="button" href="MainController?action=ListProducts">🛍 Manage Products</a></li>
        <li><a class="button" href="MainController?action=ListPromotions">🏷 Manage Promotions</a></li>
        <li><a class="button red" href="MainController?action=Logout">🚪 Logout</a></li>
    </ul>

    <h3 style="text-align: center;">🔍 Search Users</h3>

    <form action="MainController" method="get">
        <input type="hidden" name="action" value="SearchUsers" />
        <div style="text-align: center;">
            <input type="text" name="keyword" placeholder="Search by ID, name, or role" required style="width: 300px;" />
            <button type="submit">Search</button>
        </div>
    </form>

</body>
</html>
