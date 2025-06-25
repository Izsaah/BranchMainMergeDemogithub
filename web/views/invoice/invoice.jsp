<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.InvoiceDTO" %>

<%
    InvoiceDTO invoice = (InvoiceDTO) request.getAttribute("INVOICE");
    if (invoice == null) {
        response.sendRedirect("MainController?action=ListInvoices");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Invoice #<c:out value="${INVOICE.invoiceID}"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Invoice Details - ID: <c:out value="${INVOICE.invoiceID}"/></h2>

<table>
    <tr><th>Invoice ID:</th><td><c:out value="${INVOICE.invoiceID}"/></td></tr>
    <tr><th>User ID:</th><td><c:out value="${INVOICE.userID}"/></td></tr>
    <tr><th>Status:</th><td><c:out value="${INVOICE.status}"/></td></tr>
    <tr><th>Created Date:</th><td><c:out value="${INVOICE.createdDate}"/></td></tr>
    <tr><th>Total Amount:</th><td>$<c:out value="${INVOICE.totalAmount}"/></td></tr>
</table>

<hr>

<h3>Invoice Items</h3>
<c:if test="${not empty DETAILS}">
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Product ID</th>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${DETAILS}" varStatus="loop">
            <tr>
                <td>${loop.count}</td>
                <td>${item.productID}</td>
                <td>${item.quantity}</td>
                <td>$${item.price}</td>
                <td>$${item.quantity * item.price}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty DETAILS}">
    <p>No items found in this invoice.</p>
</c:if>

<p><a href="MainController?action=ListInvoices" class="button">Back to Invoice List</a></p>

</body>
</html>
