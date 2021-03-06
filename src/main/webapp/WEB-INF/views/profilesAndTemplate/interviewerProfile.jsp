<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="../../../resources/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../../resources/css/input.css"/>

    <script src="../../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="../../../resources/js/interviewerProfile.js"></script>
    <script src="../../../resources/js/header.js"></script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Interview Helper</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="/web/groups">Session</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Schedule<span class="caret"></span></a>
                    <ul id="hdrDropdownSchedule" class="dropdown-menu">
                        <!-- Populate by JS -->
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/web/interviewer">Profile</a></li>
                <li><a href="/web/logout">Log Out</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<input id="userLogin" type="hidden" value="${pageContext.request.userPrincipal.name}">

<div class="container" style="padding-top: 5em">

    <h1>My profile</h1>
    <table id="interviewerTable" class="table table-striped table-hover">
        <tbody>
            <tr><td>First Name</td><td><input id="fName"></td></tr>
            <tr><td>Last Name</td><td><input id="lName"></td></tr>
            <tr><td>E-mail</td><td><input id="eMail"></td></tr>
            <tr><td>Skype</td><td><input id="skype"></td></tr>
            <tr><td>Phone</td><td><input id="phone"></td></tr>
<%--            <tr><td>Groups</td><td id="groups"></td></tr>--%>
            <tr><td>My templates</td><td id="templates"></td></tr>
        </tbody>
    </table>

    <div id = "save">
        <a href="#" class="btn btn-default">Update profile</a>
    </div>
    <div>
        <h3 id="message"></h3>
    </div>

</div>
</body>
</html>