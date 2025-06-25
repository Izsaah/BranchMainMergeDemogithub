<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.UsersDTO" %>

<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || (!"AD".equals(user.getRoleID()) && !"AC".equals(user.getRoleID()))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Invoice List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>All Invoices - <c:out value="${LOGIN_USER.fullName}" /></h2>

<c:if test="${not empty INVOICE_LIST}">
    <table>
        <thead>
            <tr>
                <th>Invoice ID</th>
                <th>User ID</th>
                <th>Total Amount</th>
                <th>Status</th>
                <th>Created Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="invoice" items="${INVOICE_LIST}">
                <tr>
                    <td>${invoice.invoiceID}</td>
                    <td>${invoice.userID}</td>
                    <td>$${invoice.totalAmount}</td>
                    <td>${invoice.status}</td>
                    <td>${invoice.createdDate}</td>
                    <td>
                        <a href="MainController?action=ViewInvoice&invoiceID=${invoice.invoiceID}" class="button">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty INVOICE_LIST}">
    <p>No invoices found.</p>
</c:if>

<p><a href="home.jsp" class="button">Back to Home</a></p>

</body>
</html>
