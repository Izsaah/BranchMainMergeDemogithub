<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dao.DeliveryDAO, dto.DeliveryDTO" %>

<%
    Object user = session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int deliveryID = Integer.parseInt(request.getParameter("deliveryID"));
    DeliveryDAO dao = new DeliveryDAO();
    DeliveryDTO d = dao.getDeliveryByID(deliveryID);

    if (d == null) {
        request.setAttribute("errorMsg", "Delivery not found.");
        request.getRequestDispatcher("error.jsp").forward(request, response);
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Delivery</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Edit Delivery</h2>

<form action="MainController" method="get">
    <input type="hidden" name="action" value="UpdateDelivery" />
    <input type="hidden" name="deliveryID" value="<%= d.getDeliveryID() %>" />

    <label>
        Address:
        <input type="text" name="address" value="<%= d.getAddress() %>" required />
    </label><br/>

    <label>
        Delivery Date:
        <input type="date" name="deliveryDate" value="<%= d.getDeliveryDate() %>" required />
    </label><br/>

    <label>
        Status:
        <select name="status">
            <option value="pending" <%= "pending".equals(d.getStatus()) ? "selected" : "" %>>Pending</option>
            <option value="shipped" <%= "shipped".equals(d.getStatus()) ? "selected" : "" %>>Shipped</option>
            <option value="delivered" <%= "delivered".equals(d.getStatus()) ? "selected" : "" %>>Delivered</option>
        </select>
    </label><br/>

    <button type
