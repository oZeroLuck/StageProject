<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<!-- TODO: Add more functionalities -->

<html>
<head>
    <title><fmt:message key="label.userProfile"/> </title>
</head>
<body>

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

<form action="UserControllerServlet" method="GET">

    <input type="hidden" name="command" value="LOGOUT" />

    <input type="submit" value="Logout"/>

</form>

</body>
</html>
