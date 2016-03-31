<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <spring:url value="/web/login" var="loginUrl"/>

    You are now logged out!!

    <a href="${loginUrl}">Login</a>
</div>