<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Spring Security Demo</title>
    </head>
    <body>
        <h1>Welcome!</h1>
        <p>Click <a href="<spring:url value="/greeting" />">here</a> to see a greeting.</p>
    </body>
</html>