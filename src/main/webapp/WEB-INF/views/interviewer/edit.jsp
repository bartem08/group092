<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Interviewer Edit Page</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
<div id="container">
    <form:form  modelAttribute="interviewer" action="${saveURL}" method="POST">

        <form:label path="id">
            ID
        </form:label>
        <form:input path="id" />
        <p/>

        <form:label path="lastName">
            ${labelInterviewerLastName}*
        </form:label>
        <form:input path="lastName" />
        <p/>

        <form:label path="firstName">
            ${labelInterviewerFirstName}*
        </form:label>
        <form:input path="firstName" />
        <p/>

        <form:label path="email">
            ${labelInterviewertEmail}*
        </form:label>
        <form:input path="email" id="email"/>
        <p/>

        <form:label path="phone">
            ${labelInterviewerPhone}
        </form:label>
        <form:input path="phone" id="phone"/>
        <p/>

        <form:label path="skype">
            ${labelInterviewerSkype}
        </form:label>
        <form:input path="skype" id="skype"/>
        <p/>

        <button type="submit">Save</button>
        <button type="reset">Reset</button>

    </form:form>
</div>
</body>
</html>