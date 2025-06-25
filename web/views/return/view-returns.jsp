<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dto.UsersDTO" %>
<%
    UsersDTO user = (UsersDTO) session.getAttribute("LOGIN_USER");
    if (user == null || !"AD".equals(user.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Return Management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h2>Return Requests</h2>

    <c:if test="${not empty RETURN_LIST}">
        <table border="1">
            <tr>
                <th>Return ID</th>
                <th>Invoice ID</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Update</th>
            </tr>
            <c:forEach var="r" items="${RETURN_LIST}">
                <tr>
                    <td>${r.returnID}</td>
                    <td>${r.invoiceID}</td>
                    <td>${r.reason}</td>
                    <td>${r.status}</td>
                    <td>
                        <form action="MainController" method="post" style="display: inline;">
                            <input type="hidden" name="action" value="HandleReturn" />
                            <input type="hidden" name="returnID" value="${r.returnID}" />
                            <select name="status">
                                <option value="Pending" ${r.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                <option value="Approved" ${r.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                <option value="Rejected" ${r.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                            </select>
                            <button type="submit">Update</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${empty RETURN_LIST}">
        <p>No return requests found.</p>
    </c:if>

    <p><a href="admin-dashboard.jsp">Back to Dashboard</a></p>
</body>
</html>
