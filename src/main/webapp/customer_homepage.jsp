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

    <h2><fmt:message key="label.welcome"/> <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/> !</h2>

    <hr>

    <br/>

    <c:url var="addLink" value="CarParkControllerServlet">
        <c:param name ="command" value="REQUEST" />
        <c:param name ="secondCommand" value="ADD"/>
    </c:url>

    <input type="button" value="<fmt:message key="label.addReservation" />"
            onclick="window.location.href='${addLink}'; return false;" />

    <br/><br/>

    <div id="container">

        <div id="content">

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

                    <tr>
                        <td>${tempReservation.id}</td>
                        <td>${tempReservation.theVehicle.type}</td>
                        <td>${tempReservation.startDate}</td>
                        <td>${tempReservation.endDate}</td>
                        <td>
                            <c:choose>
                                <c:when test="${tempReservation.approved == 'pending'}">
                                    <fmt:message key="label.pending"/>
                                </c:when>
                                <c:otherwise>
                                    ${tempReservation.approved}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <!-- Time distance calculation -->
                        <fmt:parseNumber var="daysDifference"
                                         value="${( tempReservation.startDate.time - today.time ) / (1000*60*60*24) }"
                                         integerOnly="true" />
                        <c:choose>
                            <c:when test="${daysDifference >= 2}">
                                <td>
                                    <form action="CarParkControllerServlet" method="GET">
                                        <input type="hidden" name="command" value="REQUEST" />
                                        <input type="hidden" name="secondCommand" value="UPDATE">
                                        <input type="hidden" name="reservationId" value="${tempReservation.id}"/>
                                        <input type="submit" value="<fmt:message key="label.update"/>"/>
                                    </form>
                                    <form action="CarParkControllerServlet" method="POST">
                                        <input type="hidden" name="command" value="DELETE" />
                                        <input type="hidden" name="reservationId" value="${tempReservation.id}"/>
                                        <input type="submit" value="<fmt:message key="label.delete"/>"/>
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td><fmt:message key="label.none"/></td>
                            </c:otherwise>
                        </c:choose>
                     </tr>

                </c:forEach>

            </table>

        </div>

    </div>

</body>
</html>
<!-- Temp Code saving space

<td><a href ="${changeLink}">Update</a>


<form action="CarParkControllerServlet" method="post">
    <input type="hidden" name="command" value="DELETE">

    <a href ="${deleteLink}"
       onclick="if(!(confirm('Are you sure you want to delete this reservation?'))) return false">
        Delete</a>
</form>

-->