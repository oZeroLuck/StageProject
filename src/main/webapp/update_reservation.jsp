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
            <a href="CarParkControllerServlet"><fmt:message key="label.homepage" /></a>
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

        <h1><fmt:message key="label.update"/></h1>

        <form action="CarParkControllerServlet" method="POST">

            <input type="hidden" name="reservationId" value="${theReservation.id}">

            <input type="hidden" name="command" value="UPDATE_R">

            <fmt:message key="label.update" /> <fmt:message key="label.startDate"/> :
                <input type="date" name="newStartDate"
                    value="${theReservation.startDate}"
                    min="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />" max="2099-12-31">
            <fmt:message key="label.endDate"/> :
                <input type="date" name="newEndDate"
                    value="${theReservation.endDate}"
                    min="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />" max="2099-12-31">

            <input type="submit" name="<fmt:message key="label.update"/>">
        </form>

</body>
</html>
