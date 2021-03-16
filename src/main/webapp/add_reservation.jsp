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

<form action="CarParkControllerServlet">

    <input type="hidden" name="command" value="ADD"/>

    <h2><fmt:message key="label.selectCar" /></h2>

    <br/><br/>

    <table>
        <tr>
            <th></th>
            <th><label><fmt:message key="label.carType" /></label></th>
            <th><label><fmt:message key="label.carBrand" /></label></th>
            <th><label><fmt:message key="label.carModel" /></label></th>
            <th><label><fmt:message key="label.carPlate" /></label></th>
            <th><label> </label></th>
        </tr>
        <c:forEach var="tempVehicle" items="${vehicles_list}">

        <tr>
            <td><input type="radio" name="selected" value="${tempVehicle.id}"></td>
            <td>${tempVehicle.type}</td>
            <td>${tempVehicle.manufacturer}</td>
            <td>${tempVehicle.model}</td>
            <td>${tempVehicle.licencePlate}</td>
        </tr>

        </c:forEach>
    </table>
    <br/><br/>

    <fmt:message key="label.duration"/> :
        <select name="duration" id="duration">
            <option value="1h">1h</option>
            <option value="2h">2h</option>
            <option value="3h">3h</option>
            <option value="24h">24h</option>
        </select>

    <br/><br/>

    <input type="submit" value="<fmt:message key="label.add"/>">

</form>

</body>
</html>
