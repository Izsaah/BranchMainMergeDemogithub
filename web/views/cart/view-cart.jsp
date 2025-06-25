<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.UsersDTO, dto.CartDetailsDTO, dto.ProductsDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || !"BU".equals(user.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Cart</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>🛒 My Cart - <c:out value="${user.fullName}" /></h2>

<c:if test="${not empty CART_ITEMS}">
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="UpdateCart" />
        <table>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Actions</th>
            </tr>
            <c:set var="total" value="0" />
            <c:forEach var="item" items="${CART_ITEMS}">
                <tr>
                    <td><c:out value="${item.product.name}" /></td>
                    <td>$<c:out value="${item.product.price}" /></td>
                    <td>
                        <input type="number" name="quantity_${item.product.productID}" min="1"
                               value="${item.quantity}" style="width: 60px;" />
                    </td>
                    <td>
                        $<c:out value="${item.product.price * item.quantity}" />
                        <c:set var="total" value="${total + (item.product.price * item.quantity)}" />
                    </td>
                    <td>
                        <a class="button red" href="MainController?action=RemoveFromCart&productID=${item.product.productID}">Remove</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3" style="text-align: right;"><strong>Total:</strong></td>
                <td colspan="2"><strong>$<c:out value="${total}" /></strong></td>
            </tr>
        </table>
        <button type="submit" class="button">Update Cart</button>
        <a href="MainController?action=Checkout" class="button">Proceed to Checkout</a>
    </form>
</c:if>

<c:if test="${empty CART_ITEMS}">
    <p>Your cart is empty.</p>
</c:if>

<p><a href="MainController?action=ListProducts">← Continue Shopping</a></p>
<p><a href="home.jsp">← Back to Home</a></p>

</body>
</html>
