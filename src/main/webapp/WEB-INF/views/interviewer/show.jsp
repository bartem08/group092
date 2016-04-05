<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interviewer</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {
            var login = $("#userPrincipal").val();
            $.ajax({
                type: "GET",
                url: "/rest/interviewers/" + login + "/dto",
                success: function(result) {
                    var interviewer = JSON.stringify(result);
                    console.log("JSON: " + interviewer);
                    interviewer = JSON.parse(interviewer);

                    $("#interviewerFullName").text(interviewer.fullName);
                }
            });
        });

    </script>
</head>
<body>
<input id="userPrincipal" type="hidden" value="${pageContext.request.remoteUser}"/>
<h1>Hello, <span id="interviewerFullName"></span></h1>
<a href="/web/groups">Groups</a>
</body>
</html>