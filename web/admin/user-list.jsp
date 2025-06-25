<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.UsersDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <title>User Management</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<h2>User Management - <c:out value="${user.fullName}" /></h2>

<form action="MainController" method="get">
    <input type="hidden" name="action" value="SearchUsers" />
    <input type="text" name="keyword" placeholder="Search by ID, name, or role" value="${param.keyword}" />
    <button type="submit">Search</button>
</form>

<c:if test="${not empty USER_LIST}">
    <table>
        <thead>
            <tr>
                <th>User ID</th>
                <th>Full Name</th>
                <th>Role</th>
                <th>Email</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${USER_LIST}">
                <tr>
                    <td><c:out value="${u.userID}" /></td>
                    <td><c:out value="${u.fullName}" /></td>
                    <td><c:out value="${u.roleID}" /></td>
                    <td><c:out value="${u.email}" /></td>
                    <td><c:out value="${u.status}" /></td>
                    <td>
                        <a class="button" href="user-form.jsp?action=Edit&userID=${u.userID}">Edit</a>
                        <a class="button red" href="MainController?action=DeleteUser&userID=${u.userID}" 
                           onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty USER_LIST}">
    <p>No users found.</p>
</c:if>

<p><a class="button" href="user-form.jsp?action=Add">➕ Add New User</a></p>
<p><a href="admin-dashboard.jsp">← Back to Dashboard</a></p>

</body>
</html>
