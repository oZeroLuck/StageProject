<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Preparing the locale -->
<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<html>
<head>
    <title><fmt:message key="label.update"/></title>
</head>
<body>
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

<form action="UserControllerServlet" method="POST">
    <table>
    <input type="hidden" name="userId" value="${theUser.id}">
    <input type="hidden" name="command" value="UPDATE">
        <tr>
            <th><label><fmt:message key="label.name" /> :</label></th>
            <td><label><input type="text" name="firstName" value="${theUser.firstName}"/></label></td>
        </tr>
        <tr>
            <th><label><fmt:message key="label.lastName" /> :</label></th>
            <td><label><input type="text" name="lastName" value="${theUser.lastName}" /></label></td>
        </tr>
        <tr>
            <th><label>E-mail :</label></th>
            <td><label><input type="text" name="email" value="${theUser.email}"/></label></td>
        </tr>
        <tr>
            <th><label>Username :</label></th>
            <td><label><input type="text" name="username" value="${theUser.username}"/></label></td>
        </tr>
        <tr> <!--Note: it's a very bad and crude design -->
            <th><label>Password :</label></th>
            <td><label><input type="text" name="password" value="${theUser.password}"/></label></td>
        </tr>
    </table>
    <br/>
    <input type="submit" value="<fmt:message key="label.update"/>">
</form>

</body>
</html>
