<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.UsersDTO" %>
<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Request Return</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h2>Return Request for Invoice #<c:out value="${param.invoiceID}"/></h2>

    <form action="MainController" method="post">
        <input type="hidden" name="action" value="RequestReturn" />
        <input type="hidden" name="invoiceID" value="${param.invoiceID}" />

        <label>Reason for return:</label><br>
        <textarea name="reason" rows="4" cols="50" required></textarea><br><br>

        <button type="submit">Submit Return Request</button>
    </form>

    <p><a href="MainController?action=ListInvoices">Back to Invoices</a></p>
</body>
</html>
