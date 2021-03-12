<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<html>
<head>
    <title><fmt:message key="label.userProfile"/> </title>
</head>
<body>

<form action="CarParkControllerServlet" method="GET">

    <input type="hidden" name="command" value="LOGOUT" />

    <input type="submit" name="Submit" />

</form>

</body>
</html>
