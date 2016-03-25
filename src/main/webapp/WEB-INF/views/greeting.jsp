<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Greeting!</title>
    </head>
    <body>
        <h1>Hello <b><c:out value="${pageContext.request.remoteUser}"/></b> </h1>
        <h1><b><c:out value="${name}"/></b> </h1>
        <form action="/logout" method="post">
            <input type="submit" value="Sign Out"/>
        </form>
    <br/>
    <p>Get <a href="/mongo">message</a> from MongoDB</p>

    </body>
</html>