<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dto.CategoriesDTO, dto.UsersDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("../login.jsp");
        return;
    }

    CategoriesDTO cat = (CategoriesDTO) request.getAttribute("CATEGORY");
    boolean isEdit = (cat != null);
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= isEdit ? "Update" : "Add" %> Category</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<h2><%= isEdit ? "Update" : "Add" %> Category</h2>

<form action="MainController" method="post">
    <input type="hidden" name="action" value="<%= isEdit ? "UpdateCategory" : "AddCategory" %>"/>

    <% if (isEdit) { %>
        <input type="hidden" name="categoryID" value="<%= cat.getCategoryID() %>"/>
    <% } %>

    <label for="categoryName">Name:</label>
    <input type="text" name="categoryName" id="categoryName" required
           value="<%= isEdit ? cat.getCategoryName() : "" %>" />

    <label for="description">Description:</label>
    <textarea name="description" id="description" required rows="4"><%= isEdit ? cat.getDescription() : "" %></textarea>

    <input type="submit" value="<%= isEdit ? "Update" : "Add" %> Category"/>
</form>

<p style="text-align: center;"><a class="button" href="MainController?action=ListCategories">Back to Category List</a></p>

</body>
</html>
