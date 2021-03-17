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
    <title><fmt:message key="label.addVehicle"/></title>
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
        <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
    </div>
</div>

<hr>

<form action="CarParkControllerServlet" method="POST">

    <input type="hidden" name="command" value="ADD_V"/>

    <!-- TODO: Add dropdown menu for brand and model fields, bind models to certain brands -->
    <!-- Maybe add type based on the model? -->

    <table>
        <tr>
            <td><label><fmt:message key="label.carBrand" /> :</label></td>
            <td><label><input type="text" name="brand" /></label></td>
        </tr>
        <tr>
            <td><label><fmt:message key="label.carModel" /> :</label></td>
            <td><label><input type="text" name="model" /></label></td>
        </tr>
        <tr>
            <td><label><fmt:message key="label.carPlate"/></label></td>
            <td><label><input type="text" name="plate" /></label></td>
        </tr>
        <tr>
            <td><label><fmt:message key="label.carType"/></label></td>
            <td><label><input type="text" name="type" /></label></td>
        </tr>
    </table>

    <input type="submit" name="<fmt:message key="label.registerNow"/>" />

    <hr>

</form>

</body>
</html>
