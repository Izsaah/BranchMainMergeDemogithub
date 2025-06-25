<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    Object user = session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Delivery List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Delivery List</h2>

<c:if test="${not empty successMsg}">
    <div class="success">${successMsg}</div>
</c:if>
<c:if test="${not empty errorMsg}">
    <div class="error">${errorMsg}</div>
</c:if>

<c:if test="${not empty DELIVERY_LIST}">
    <table border="1" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>Invoice ID</th>
            <th>Address</th>
            <th>Delivery Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="d" items="${DELIVERY_LIST}">
            <tr>
                <td>${d.deliveryID}</td>
                <td>${d.invoiceID}</td>
                <td>${d.address}</td>
                <td>${d.deliveryDate}</td>
                <td>${d.status}</td>
                <td>
                    <a class="button" href="edit-delivery.jsp?deliveryID=${d.deliveryID}">Edit</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty DELIVERY_LIST}">
    <p>No deliveries found.</p>
</c:if>

<p><a href="home.jsp">Back to Home</a></p>

</body>
</html>
