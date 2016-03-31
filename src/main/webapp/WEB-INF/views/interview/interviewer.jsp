<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interviewer</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {
            var login = $("#userPrincipal").val();
            $.ajax({
                type: "GET",
                url: "/rest/interviewers/login/dto/" + login,
                success: function(result) {
                    var interviewer = JSON.stringify(result);
                    console.log("JSON: " + interviewer);
                    interviewer = JSON.parse(interviewer);

                    $("#interviewerName").text(interviewer.name);
                }
            });
        });

    </script>
</head>
<body>
<input id="userPrincipal" type="hidden" value="${pageContext.request.remoteUser}"/>
<h1>Hello, <span id="interviewerName"></span></h1>
<a href="/web/groups">Groups</a>
</body>
</html>