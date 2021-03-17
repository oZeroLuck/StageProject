<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<html>
<head>
    <title><fmt:message key="label.registrationForm"/></title>
</head>
<body>


<!-- Header -->
<div id="wrapper">
    <div id="header">
        <a href="customer_homepage.jsp"><fmt:message key="label.homepage" /></a>
        |
        <a href="customer_car_park.jsp"><fmt:message key="label.carPark" /></a>
        |
        <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
    </div>
</div>

<hr>

<form action="UserControllerServlet" method="POST">

    <input type="hidden" name="command" value="REG" />

    <table>
        <tr>
            <td><label><fmt:message key="label.name" /> :</label></td>
            <td><label><input type="text" name="firstName" /></label></td>
        </tr>
        <tr>
            <td><label><fmt:message key="label.lastName" /> :</label></td>
            <td><label><input type="text" name="lastName" /></label></td>
        </tr>
        <tr>
            <td><label>E-mail :</label></td>
            <td><label><input type="text" name="email" /></label></td>
        </tr>
        <tr>
            <td><label>Username :</label></td>
            <td><label><input type="text" name="username" /></label></td>
        </tr>
        <tr>
            <td><label>Password :</label></td>
            <td><label><input type="text" name="password" /></label></td>
        </tr>
    </table>

    <input type="submit" name="<fmt:message key="label.registerNow"/>" />

    <hr>

    <a href="login.jsp"><fmt:message key="label.back"/></a>

</form>

</body>
</html>
