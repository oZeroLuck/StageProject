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
            <a href="customer_homepage.jsp"><fmt:message key="label.homepage" /></a>
             |
            <a href="customer_car_park.jsp"><fmt:message key="label.carPark" /></a>
             |
            <a href="customer_profile.jsp"><fmt:message key="label.userProfile" /></a>
        </div>
    </div>

    <hr>

        <h1><fmt:message key="label.update"/></h1>

        <form action="CarParkControllerServlet" method="GET">

            <input type="hidden" name="command" value="UPDATE">

            <table>
                <tr>
                    <td></td>
                </tr>
            </table>

        </form>

</body>
</html>
