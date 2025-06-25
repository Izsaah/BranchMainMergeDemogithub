<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<h2>Login</h2>

<form action="MainController" method="post">
    <input type="hidden" name="action" value="Login" />

    <label for="userID">User ID</label>
    <input type="text" name="userID" id="userID" required />

    <label for="password">Password</label>
    <input type="password" name="password" id="password" required />

    <input type="submit" value="Login" />
</form>

<c:if test="${not empty errorMsg}">
    <p style="color: red; text-align: center;"><c:out value="${errorMsg}" /></p>
</c:if>

</body>
</html>
