<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <spring:url value="/web/login" var="loginUrl"/>

    <h1>Unauthorized Access !!</h1>
    <hr />

    <c:if test="${not empty error}">
        <div style="color:red">
            Your fake login attempt was bursted, dare again !!<br />
            Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>

    <a href="${loginUrl}">Login</a>
</div>