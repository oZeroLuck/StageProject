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
        <c:choose>
            <c:when test="${user.isAdmin}">
                <a href="UserControllerServlet"><fmt:message key="label.homepage" /></a>
            </c:when>
            <c:otherwise>
                <a href="customer_homepage.jsp"><fmt:message key="label.homepage" /></a>
            </c:otherwise>
        </c:choose>
        |
        <c:url var="parkLink" value="CarParkControllerServlet">
            <c:param name="command" value="CAR_PARK"/>
        </c:url>
        <a href="${parkLink}"><fmt:message key="label.carPark" /></a>
        |
        <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
    </div>
</div>

<hr>

<c:if test="${user.isAdmin}">
    <input type="button" value="<fmt:message key="label.addVehicle"/>"
           onclick="window.location.href='add_vehicle.jsp'; return false;" />
    <hr>
</c:if>

<table>
    <tr>
        <th><label><fmt:message key="label.carType" /></label></th>
        <th><label><fmt:message key="label.carBrand" /></label></th>
        <th><label><fmt:message key="label.carModel" /></label></th>
        <th><label><fmt:message key="label.carPlate" /></label></th>
        <c:if test="${user.isAdmin}">
            <th><label><fmt:message key="label.action"/> </label></th>
        </c:if>
    </tr>
    <c:forEach var="tempVehicle" items="${vehicles_list}">
        <tr>
            <td>${tempVehicle.type}</td>
            <td>${tempVehicle.brand}</td>
            <td>${tempVehicle.model}</td>
            <td>${tempVehicle.licencePlate}</td>
            <c:if test="${user.isAdmin}">
                <td>
                    <form action="CarParkControllerServlet" method="GET">
                        <input type="hidden" name="command" value="LOAD_V">
                        <input type="hidden" name="vehicleId" value="${tempVehicle.id}">
                        <input type="submit" value="<fmt:message key="label.update"/>">
                    </form>
                </td>
                <td>
                    <form action="CarParkControllerServlet" method="POST">
                        <input type="hidden" name="command" value="DELETE_V">
                        <input type="hidden" name="vehicleId" value="${tempVehicle.id}">
                        <input type="submit" value="<fmt:message key="label.delete"/>">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>

</body>
</html>
