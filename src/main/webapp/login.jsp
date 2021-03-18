<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${theLocale}" />

<fmt:setBundle basename="labels" />

<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h1>Login</h1>

<form action="UserControllerServlet" method="GET">

    <input type="hidden" name="command" value="LOGIN">

    Username:<input type="text" name="username"><br/><br/>
    Password:<input type="password" name="userpassword"><br/><br/>

    <input type="submit" name="Login">

</form>

<hr>

${message}

</body>
</html>