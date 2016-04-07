<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

  <c:if test="${not empty message}">
    <div style="color:red">
        Dear <strong>${username},</strong>
        <strong>${message}</strong>
    </div>
  </c:if>

  <a href="/web/logout">Logout</a>
</div>