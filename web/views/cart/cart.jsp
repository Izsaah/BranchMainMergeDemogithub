<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.UsersDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Your Shopping Cart</h2>

<c:if test="${not empty successMsg}">
    <p style="color: green;"><c:out value="${successMsg}" /></p>
</c:if>

<c:if test="${not empty CART_ITEMS}">
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="UpdateCartBatch" />
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="total" value="0" />
                <c:forEach var="item" items="${CART_ITEMS}">
                    <tr>
                        <td>${item.productID}</td>
                        <td>${item.productName}</td>
                        <td>${item.price}</td>
                        <td>
                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="UpdateCart" />
                                <input type="hidden" name="productID" value="${item.productID}" />
                                <input type="number" name="quantity" value="${item.quantity}" min="1" required style="width: 60px;" />
                                <input type="submit" value="Update" />
                            </form>
                        </td>
                        <td>${item.price * item.quantity}</td>
                        <td>
                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="RemoveFromCart" />
                                <input type="hidden" name="productID" value="${item.productID}" />
                                <input type="submit" value="Remove" />
                            </form>
                        </td>
                    </tr>
                    <c:set var="total" value="${total + (item.price * item.quantity)}" />
                </c:forEach>
            </tbody>
        </table>
        <h3>Total: $<c:out value="${total}" /></h3>
    </form>

    <form action="MainController" method="post">
        <input type="hidden" name="action" value="Checkout" />
        <input type="submit" value="Proceed to Checkout" class="button" />
    </form>

</c:if>

<c:if test="${empty CART_ITEMS}">
    <p>Your cart is currently empty.</p>
</c:if>

<p><a href="MainController?action=ListProducts">Continue Shopping</a></p>

</body>
</html>
