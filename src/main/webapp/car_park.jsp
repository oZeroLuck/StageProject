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
    <title> <fmt:message key="label.homepage" /> </title>
</head>
<body>

<div id="wrapper">
    <div id="header">
        <a href="customer_homepage.jsp"><fmt:message key="label.homepage" /></a>
        |
        <c:url var="parkLink" value="PageControllerServlet">
            <c:param name="command" value="CAR_PARK"/>
        </c:url>
        <a href="${parkLink}"><fmt:message key="label.carPark" /></a>
        |
        <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
    </div>
</div>

<hr>

<table>
    <tr>
        <th><label><fmt:message key="label.carType" /></label></th>
        <th><label><fmt:message key="label.carBrand" /></label></th>
        <th><label><fmt:message key="label.carModel" /></label></th>
        <th><label><fmt:message key="label.carPlate" /></label></th>
    </tr>
    <c:forEach var="tempVehicle" items="${vehicles_list}">

        <tr>
            <td>${tempVehicle.type}</td>
            <td>${tempVehicle.manufacturer}</td>
            <td>${tempVehicle.model}</td>
            <td>${tempVehicle.licencePlate}</td>
        </tr>

    </c:forEach>
</table>

</body>
</html>
