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

<form action="CarParkControllerServlet" method="POST">

    <input type="hidden" name="command" value="V_ACTION">

    <c:choose>
        <c:when test="${isAdd}">
            <input type="hidden" name="secondCommand" value="ADD">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="command" value="UPDATE"/>
            <input type="hidden" name="vehicleId" value="${theVehicle.id}">
        </c:otherwise>
    </c:choose>

    <table>
        <tr>
            <th><label><fmt:message key="label.carBrand" /> :</label></th>
            <td><label><input type="text" name="brand" value="${theVehicle.brand}"/></label></td>
        </tr>
        <tr>
            <th><label><fmt:message key="label.carModel" /> :</label></th>
            <td><label><input type="text" name="model" value="${theVehicle.model}"/></label></td>
        </tr>
        <tr>
            <th><label><fmt:message key="label.carPlate"/></label></th>
            <td><label><input type="text" name="plate" value="${theVehicle.licencePlate}"/></label></td>
        </tr>
        <tr>
            <th><label><fmt:message key="label.carType"/></label></th>
            <td><label><input type="text" name="type" value="${theVehicle.type}"/></label></td>
        </tr>
    </table>
    <c:choose>
        <c:when test="${isAdd}">
            <input type="submit" name="<fmt:message key="label.addVehicle"/>"/>
        </c:when>
        <c:otherwise>
            <input type="submit" name="<fmt:message key="label.update"/>"/>
        </c:otherwise>
    </c:choose>
    <hr>

</form>
</body>
</html>
