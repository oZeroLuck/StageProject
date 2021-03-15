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
    <title><fmt:message key="label.addReservation" /></title>
</head>
<body>

<form action="CarParkControllerServlet">

    <h2><fmt:message key="label.selectCar" /></h2>

    <br/><br/>

    <table>
        <tr>
            <th><label><fmt:message key="label.carType" /></label></th>
            <th><label><fmt:message key="label.carBrand" /></label></th>
            <th><label><fmt:message key="label.carModel" /></label></th>
            <th><label><fmt:message key="label.carPlate" /></label></th>
            <th><label> </label></th>
        </tr>
        <c:forEach var="tempVehicle" items="${vehicles_list}">
            <c:url var="addLink" value="CarParkControllerServlet">
                <c:param name="command" value="ADD" />
                <c:param name="vehicleId" value="${tempVehicle.id}"/>
            </c:url>

        <tr>
            <td>${tempVehicle.type}</td>
            <td>${tempVehicle.manufacturer}</td>
            <td>${tempVehicle.model}</td>
            <td>${tempVehicle.licencePlate}</td>
            <td><a href="${addLink}"><fmt:message key="label.add"/></a></td>
        </tr>

        </c:forEach>
    </table>


</form>

</body>
</html>
