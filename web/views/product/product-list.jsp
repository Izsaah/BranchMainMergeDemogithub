<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.ProductsDTO, dto.UsersDTO, java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Product List - <c:out value="${user.fullName}" /></h2>

<c:if test="${user.roleID == 'BU'}">
    <p><a class="button" href="MainController?action=ViewCart">🛒 View Cart</a></p>
</c:if>

<form action="MainController" method="get">
    <input type="hidden" name="action" value="SearchProducts" />
    <label>
        Name: <input type="text" name="keyword" value="${param.keyword}" />
    </label>
    <label>
        Category:
        <select name="categoryID">
            <option value="">-- All --</option>
            <c:forEach var="cat" items="${CATEGORY_LIST}">
                <option value="${cat.categoryID}"
                        <c:if test="${param.categoryID == cat.categoryID}">selected</c:if>>
                    ${cat.categoryName}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        Status:
        <select name="status">
            <option value="">-- All --</option>
            <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
            <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
        </select>
    </label>
    <label>
        Min Price: <input type="number" name="minPrice" step="0.01" value="${param.minPrice}" />
    </label>
    <label>
        Max Price: <input type="number" name="maxPrice" step="0.01" value="${param.maxPrice}" />
    </label>
    <button type="submit">Search</button>
</form>

<hr/>

<c:if test="${not empty PRODUCT_LIST}">
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Category ID</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Seller ID</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${PRODUCT_LIST}">
            <tr>
                <td><c:out value="${product.productID}" /></td>
                <td><c:out value="${product.name}" /></td>
                <td><c:out value="${product.categoryID}" /></td>
                <td><c:out value="${product.price}" /></td>
                <td><c:out value="${product.quantity}" /></td>
                <td><c:out value="${product.sellerID}" /></td>
                <td><c:out value="${product.status}" /></td>
                <td>
                    <c:if test="${user.roleID == 'AD' || user.roleID == 'SE'}">
                        <a class="button" href="product-form.jsp?action=Edit&productID=${product.productID}">Edit</a>
                        <a class="button red" href="MainController?action=DeleteProduct&productID=${product.productID}"
                           onclick="return confirm('Are you sure to delete this product?');">Delete</a>
                    </c:if>

                    <c:if test="${user.roleID == 'BU'}">
                        <form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="AddToCart" />
                            <input type="hidden" name="productID" value="${product.productID}" />
                            <input type="number" name="quantity" value="1" min="1" style="width: 60px;" />
                            <button type="submit" class="button">Add to Cart</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty PRODUCT_LIST}">
    <p>No products found.</p>
</c:if>

<c:if test="${user.roleID == 'SE' || user.roleID == 'AD'}">
    <p><a class="button" href="product-form.jsp?action=Add">Add New Product</a></p>
</c:if>

<p><a href="home.jsp">Back to Home</a></p>

</body>
</html>
