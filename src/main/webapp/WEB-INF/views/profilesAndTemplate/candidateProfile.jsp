<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="../../../resources/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../../resources/css/input.css"/>

    <script src="../../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="../../../resources/js/header.js"></script>
    <script src="../../../resources/js/candidateProfile.js"></script>
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
                <li><a href="/web/profile">Profile</a></li>
                <li><a href="/login">Log Out</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container" style="padding-top: 5em">
    <input type="hidden" id="idHidden" value="${id}">
    <h1>Candidate</h1>
    <table id="interviewerTable" class="table table-striped table-hover">
        <tbody>
        <tr><td>Surname</td><td><input id="surname"></td></tr>
        <tr><td>Name</td><td><input id="name"></td></tr>
        <tr><td>Patronymic</td><td><input id="patronymic"></td></tr>
        <tr><td>E-mail</td><td><input id="eMail"></td></tr>
        <tr><td>Skype</td><td><input id="skype"></td></tr>
        <tr><td>Phone</td><td><input id="phone"></td></tr>
        </tbody>
    </table>

    <div id = "save">
        <a href="#" class="btn btn-default">Update candidate profile</a>
    </div>
    <div>
        <h3 id="message"></h3>
    </div>
</div>
</body>
</html>