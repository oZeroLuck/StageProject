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
        <a href="UserControllerServlet"><fmt:message key="label.homepage" /></a>
        |
        <c:url var="parkLink" value="CarParkControllerServlet">
            <c:param name="command" value="CAR_PARK"/>
        </c:url>
        <a href="${parkLink}"><fmt:message key="label.carPark" /></a>
        |
        <c:url var="profile" value="UserControllerServlet">
            <c:param name="command" value="PROFILE"/>
        </c:url>
        <a href="${profile}"><fmt:message key="label.profile" /></a>
    </div>
</div>

<hr>

<h2><fmt:message key="label.welcome"/> <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/> !</h2>

<hr>

<input type="button" value="<fmt:message key="label.addCustomer"/>"
       onclick="window.location.href='add_customer.jsp'; return false;"/>

<br/><br/>

<table>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.lastName"/></th>
        <th>E-mail</th>
        <th>Username</th>
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
                    <input type="hidden" name="userId" value="${tempCustomer.id}">
                    <input type="submit" value="<fmt:message key="label.update"/>">
                </form>
            </td>
            <td>
                <form action="UserControllerServlet" method="POST">
                    <input type="hidden" name="command" value="DELETE">
                    <input type="hidden" name="userId" value="${tempCustomer.id}">
                    <input type="submit" value="<fmt:message key="label.delete"/>">
                </form>
            </td>
            <td>
                <form action="CarParkControllerServlet" method="GET">
                    <input type="hidden" name="command" value="LIST">
                    <input type="hidden" name="userId" value="${tempCustomer.id}">
                    <input type="submit" value="<fmt:message key="label.reservations"/>">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>

</body>
</html>
