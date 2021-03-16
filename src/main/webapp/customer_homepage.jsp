<%@ page import="com.rental.entity.User" %>
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
            <a href="customer_homepage.jsp"><fmt:message key="label.homepage" /></a>
             |
            <a href="customer_car_park.jsp"><fmt:message key="label.carPark" /></a>
             |
            <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
        </div>
    </div>

    <hr>

    <h2><fmt:message key="label.welcome"/> <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/> !</h2>

    <hr>

    <br/>

    <c:url var="addLink" value="CarParkControllerServlet">
        <c:param name ="command" value="LOAD_CARS" />
    </c:url>

    <input type="button" value="<fmt:message key="label.addReservation" />"
            onclick="window.location.href='${addLink}'; return false;"
            class="add_student_button" />

    <br/><br/>

    <div id="container">

        <div id="content">

            <table>

                <tr>
                    <th><fmt:message key="label.reservation" /></th>
                    <th><fmt:message key="label.carType" /></th>
                    <th><fmt:message key="label.startDate" /></th>
                    <th><fmt:message key="label.duration" /></th>
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
                        <c:param name="reservationId" value="${tempReservation.id}"/>
                    </c:url>



                    <tr>
                        <td>${tempReservation.id}</td>
                        <td>${tempReservation.theVehicle.type}</td>
                        <td>${tempReservation.startDate}</td>
                        <td>${tempReservation.duration}</td>
                        <td><a href ="${changeLink}">Update</a>
                             | <!-- Setting a confirmation message -->
                        <a href ="${deleteLink}"
                           onclick="if(!(confirm('Are you sure you want to delete this reservation?'))) return false">
                                Delete</a> </td>
                     </tr>

                </c:forEach>

            </table>

        </div>

    </div>

</body>
</html>
