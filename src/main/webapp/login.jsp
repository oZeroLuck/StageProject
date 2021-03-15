<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h1>Login</h1>

<form action="UserControllerServlet" method="POST">

    Name:<input type="text" name="username"><br/><br/>
    Password:<input type="text" name="userpassword"><br/><br/>

    <input type="submit" name="Login"> | <a href="customer_registration.jsp"><fmt:message key="label.registerNow"/></a>

</form>

<!-- temporary -->

<hr>

<table>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Email</th>
        <th>Password</th>
    </tr>

    <c:forEach var="user" items="${users}">
    <tr>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.email}</td>
        <td>${user.password}</td>
    </tr>
    </c:forEach>
</table>

</body>
</html>