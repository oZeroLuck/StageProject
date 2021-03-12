<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<!-- Formatting type -->
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
            <a href="customer_car_park.jsp"><fmt:message key="label.carPark" /></a>
             |
            <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
        </div>
    </div>

    <hr>

    <br/>

    <input type="button" value="<fmt:message key="label.addReservation" />"
            onclick="window.location.href='add_reservation.jsp'; return false;"
            class="add_student_button" />

    <br/><br/>

    <div id="container">

        <div id="content">

            <table>

                <tr>
                    <th><fmt:message key="label.reservation" /></th>
                    <th><fmt:message key="label.carType" /></th>
                    <th><fmt:message key="label.startDate" /></th>
                    <th><fmt:message key="label.action" /></th>
                </tr>

                <c:forEach var="tempReservation" items="${reservation_list}">

                    <!-- Setting up links for every reservation -->
                    <c:url var="changeLink" value="CarParkControllerServlet">
                        <c:param name="command" value="LOAD" />
                        <c:param name="reservationId" value="${tempReservation.id}"/>
                    </c:url>

                    <!-- Setting up temporary delete links for every reservation -->
                    <c:url var="deleteLink" value="CarParkControllerServlet">
                        <c:param name="command" value="DELETE" />
                        <c:param name="reservationId" value="${tempReservation.it}"/>
                    </c:url>

                </c:forEach>

                <tr>
                    <td>${tempReservation.id}</td>
                    <td>${tempReservation.car.type}</td>
                    <td>${tempReservation.date}</td>
                    <td>${changeLink}</td>
                    <td>${deleteLink}</td>
                </tr>

            </table>

        </div>

    </div>

</body>
</html>
