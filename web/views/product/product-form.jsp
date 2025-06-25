<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.UsersDTO" %>

<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || (!"SE".equals(user.getRoleID()) && !"AD".equals(user.getRoleID()))) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${param.action eq 'Edit'}">Edit Product</c:when>
            <c:otherwise>Add New Product</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<h2>
    <c:choose>
        <c:when test="${param.action eq 'Edit'}">Edit Product</c:when>
        <c:otherwise>Add New Product</c:otherwise>
    </c:choose>
</h2>

<form action="MainController" method="post">
    <input type="hidden" name="action"
           value="<c:out value='${param.action eq "Edit" ? "UpdateProduct" : "AddProduct"}'/>"/>

    <c:if test="${not empty PRODUCT}">
        <input type="hidden" name="productID" value="${PRODUCT.productID}" />
    </c:if>

    <label for="name">Product Name</label>
    <input type="text" name="name" id="name" required value="${PRODUCT.name != null ? PRODUCT.name : ''}" />

    <label for="categoryID">Category</label>
    <select name="categoryID" id="categoryID" required>
        <option value="">-- Select Category --</option>
        <c:forEach var="cat" items="${CATEGORY_LIST}">
            <option value="${cat.categoryID}"
                    <c:if test="${PRODUCT != null && PRODUCT.categoryID == cat.categoryID}">selected</c:if>>
                ${cat.categoryName}
            </option>
        </c:forEach>
    </select>

    <label for="price">Price</label>
    <input type="number" step="0.01" name="price" id="price" required value="${PRODUCT.price != null ? PRODUCT.price : ''}" />

    <label for="quantity">Quantity</label>
    <input type="number" name="quantity" id="quantity" required value="${PRODUCT.quantity != null ? PRODUCT.quantity : ''}" />

    <label for="status">Status (active/inactive)</label>
    <input type="text" name="status" id="status" required value="${PRODUCT.status != null ? PRODUCT.status : 'active'}" />

    <input type="submit" value="<c:out value='${param.action eq "Edit" ? "Update" : "Add"}'/> Product" />
</form>

<p style="text-align: center;"><a href="MainController?action=ListProducts">Back to Product List</a></p>

</body>
</html>
