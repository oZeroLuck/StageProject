<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:remove var="sessionCommand" scope="session"/>
<c:remove var="secondCommand" scope="session"/>
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
        <c:url var="hpLink" value="CarParkControllerServlet">
            <c:param name="command" value=" "/>
        </c:url>
        <a href="${hpLink}"><fmt:message key="label.homepage" /></a>
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

<form action="CarParkControllerServlet" method="post">

    <input type="hidden" name="command" value="CHANGE_R"/>
    <input type="hidden" name="secondCommand" value="ADD">

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
                <td>${tempVehicle.brand}</td>
                <td>${tempVehicle.model}</td>
                <td>${tempVehicle.licencePlate}</td>
            </tr>

        </c:forEach>

    </table>
    <br/><br/>

    <!-- TODO: Find a way to set dates for start and end -->

    <fmt:message key="label.startDate"/> :
    <input type="date" id="start" name="startDate"
           value="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />"
           min="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />" max="2099-12-31">
    |
    <fmt:message key="label.endDate" />:
    <input type="date" id="end" name="endDate"
           value="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />"
           min="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />" max="2099-12-31">
    <br/><br/>

    <input type="submit" value="<fmt:message key="label.addReservation"/>">

</form>

${message}
<c:remove var="message" scope="session"/>

</body>
</html>
