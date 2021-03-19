<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<html>
<head>
    <title><fmt:message key="label.profile"/> </title>
</head>
<body>

<div id="wrapper">
    <div id="header">
        <a href="CarParkControllerServlet"><fmt:message key="label.homepage" /></a>
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
<table>

    <tr>
        <th><label><fmt:message key="label.name" /> :</label></th>
        <td><label>${user.firstName}</label></td>
    </tr>
    <tr>
        <th><label><fmt:message key="label.lastName" /> :</label></th>
        <td><label>${user.lastName}</label></td>
    </tr>
    <tr>
        <th><label>E-mail :</label></th>
        <td><label>${user.email}</label></td>
    </tr>
    <tr>
        <th><label>Username :</label></th>
        <td><label>${user.email}</label></td>
    </tr>

</table>

<hr>

<!-- Can I make a single form? -->

<table>
    <tr>
        <form action="UserControllerServlet" method="GET">
            <input type="hidden" name="userId" value="${user.id}">
            <input type="hidden" name="command" value="LOAD">
            <input type="submit" value="<fmt:message key="label.update"/>">
        </form>
    </tr>
    <tr>
        <form action="UserControllerServlet" method="GET">
            <input type="hidden" name="command" value="LOGOUT" />
            <input type="submit" value="Logout"/>
        </form>
    </tr>
</table>
</body>
</html>
