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
    <title><fmt:message key="label.customerReservation"/></title>
</head>
<body>
<!-- Header -->
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
<form action="CarParkControllerServlet" method="POST">
    <input type="hidden" name="command" value="DECIDE">
    <table>
        <tr>
            <th><fmt:message key="label.reservation" /></th>
            <th><fmt:message key="label.carType" /></th>
            <th><fmt:message key="label.startDate" /></th>
            <th><fmt:message key="label.endDate" /></th>
            <th><fmt:message key="label.approved"/></th>
            <th><fmt:message key="label.action" /></th>
        </tr>

        <c:forEach var="tempReservation" items="${reservation_list}">
        <input type="hidden" name="reservationId" value="${tempReservation.id}">

            <tr>
                <td>${tempReservation.id}</td>
                <td>${tempReservation.theVehicle.type}</td>
                <td>${tempReservation.startDate}</td>
                <td>${tempReservation.endDate}</td>
                <td>
                    <c:choose>
                        <c:when test="${empty tempReservation.approved}">
                            <fmt:message key="label.pending"/>
                        </c:when>
                        <c:otherwise>
                            ${tempReservation.approved}
                        </c:otherwise>
                    </c:choose>
                </td>
                    <c:if test="${empty tempReservation.approved}">
                        <td>
                            <input type="hidden" name="verdict" value="Approved">
                            <input type="submit" value="<fmt:message key="label.approve"/>">
                        </td>
                        <td>
                            <input type="hidden" name="verdict" value="Denied">
                            <input type="submit" value="<fmt:message key="label.deny"/>">
                        </td>
                    </c:if>
                </tr>
        </c:forEach>
    </table>
</form>
</body>

</html>