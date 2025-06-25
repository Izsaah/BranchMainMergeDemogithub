<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.UsersDTO" %>

<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Category Management</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<h2>Category Management</h2>

<!-- 🔍 Search Form -->
<form action="MainController" method="get">
    <input type="hidden" name="action" value="SearchCategories"/>
    <input type="text" name="keyword" placeholder="Search by name" value="${param.keyword}" />
    <input type="submit" value="Search" />
</form>

<!-- 📋 Category Table -->
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Action</th>
    </tr>
    <c:forEach var="cat" items="${CATEGORY_LIST}">
        <tr>
            <td><c:out value="${cat.categoryID}" /></td>
            <td><c:out value="${cat.categoryName}" /></td>
            <td><c:out value="${cat.description}" /></td>
            <td>
                <form action="MainController" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="DeleteCategory" />
                    <input type="hidden" name="categoryID" value="${cat.categoryID}" />
                    <input type="submit" class="button red" value="Delete"
                           onclick="return confirm('Are you sure to delete this category?');" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- ➕ Add Category -->
<h3>Add Category</h3>
<form action="MainController" method="post">
    <input type="hidden" name="action" value="AddCategory" />
    <label>Category Name:</label>
    <input type="text" name="categoryName" required />
    
    <label>Description:</label>
    <input type="text" name="description" required />
    
    <input type="submit" value="Add Category" />
</form>

<p style="text-align: center;"><a class="button" href="home.jsp">Back to Home</a></p>

</body>
</html>
