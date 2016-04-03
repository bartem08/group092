<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

  <spring:url value="/web/login" var="loginUrl"/>

  <c:if test="${not empty message}">
    <div style="color:red">
        <strong>${username},</strong>
        <strong>${message}</strong>
    </div>
  </c:if>

  <a href="${loginUrl}">Login</a>
</div>