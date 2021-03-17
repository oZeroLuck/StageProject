<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<!--Preparing the locale -->
<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<html>
<head>
    <title> <fmt:message key="label.homepage" /> </title>
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

<h2><fmt:message key="label.welcome"/> <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/> !</h2>

<hr>

<input type="button" value="<fmt:message key="label.addCustomer"/>"
       onclick="window.location.href='customer_registration.jsp'; return false;"/>

<br/><br/>

<table>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.lastName"/></th>
        <th>E-mail</th>
        <th><fmt:message key="label.action"/></th>
    </tr>

    <c:forEach var="tempCustomer" items="${customerList}">

        <tr>
            <td>${tempCustomer.firstName}</td>
            <td>${tempCustomer.lastName}</td>
            <td>${tempCustomer.email}</td>
            <td>${tempCustomer.username}</td>
            <td>
                <form action="UserControllerServlet" method="GET">
                    <input type="hidden" name="command" value="LOAD">
                    <input type="hidden" name="customerID" value="${tempCustomer.id}">
                    <input type="submit" value="<fmt:message key="label.update"/>">
                </form>
            </td>
            <td>
                <form action="UserControllerServlet" method="POST">
                    <input type="hidden" name="command" value="DELETE">
                    <input type="hidden" name="customerId" value="${tempCustomer.id}">
                    <input type="submit" value="<fmt:message key="label.delete"/>">
                </form>
            </td>
            <td>
                <form action="CarParkControllerServlet" method="GET">
                    <input type="hidden" name="command" value="LIST">
                    <input type="hidden" name="customerId" value="${tempCustomer.id}">
                    <input type="submit" value="<fmt:message key="label.reservations"/>">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>

</body>
</html>
