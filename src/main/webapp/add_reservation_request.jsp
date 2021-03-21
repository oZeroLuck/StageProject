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
    <c:choose>
        <c:when test="${secondCommand == 'ADD'}">
            <title><fmt:message key="label.addReservation" /></title>
        </c:when>
        <c:otherwise>
            <title><fmt:message key="label.update"/></title>
        </c:otherwise>
    </c:choose>
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

<form action="CarParkControllerServlet" method="POST">

    <input type="hidden" name="command" value="CHANGE_R">
    <input type="hidden" name="secondCommand" value="${secondCommand}">
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
        <c:if test="${secondCommand == 'UPDATE'}">
            <c:set var="theRes" value="${theReservation}"/>
            <input type="hidden" name="reservationId" value=${theRes.id}>
        </c:if>
        <c:forEach var="tempVehicle" items="${vehicles_list}">

            <tr>
                <td>
                    <input type="radio" name="selected" value="${tempVehicle.id}"
                        ${tempVehicle.id==theRes.theVehicle.id ? 'checked':'checked'}>
                </td>
                <td>${tempVehicle.type}</td>
                <td>${tempVehicle.brand}</td>
                <td>${tempVehicle.model}</td>
                <td>${tempVehicle.licencePlate}</td>
            </tr>

        </c:forEach>

    </table>
    <br/><br/>

    <c:choose>
        <c:when test="${secondCommand == 'ADD'}">
            <c:set var="theStartDate" value="${now}"/>
            <c:set var="theEndDate" value="${now}" />
        </c:when>
        <c:otherwise>
            <c:set var="theStartDate" value="${sDateValue}"/>
            <c:set var="theEndDate" value="${eDateValue}" />
        </c:otherwise>
    </c:choose>

    <fmt:message key="label.startDate"/>
    <input type="date" id="start" name="startDate"
           value="${theStartDate}" pattern="yyyy-MM-dd"
           min="${now}" max="2099-12-31">
    |
    <fmt:message key="label.endDate" />
    <input type="date" id="end" name="endDate"
           value="${theEndDate}" pattern="yyyy-MM-dd"
           min="${now}" max="2099-12-31">
    <br/><br/>


    <input type="submit" value="<fmt:message key="label.addReservation"/>">


</form>

</body>
</html>
