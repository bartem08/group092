<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="../../resources/js/header.js"></script>
    <script src="../../resources/js/groups.js"></script>
</head>
<body>

<input id="userPrincipal" type="hidden" value="${pageContext.request.userPrincipal.name}">

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
                <li><a id="interviewerProfile">Profile</a></li>
                <li><a href="/web/logout">Log Out</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container" style="padding-top: 5em">
    <h1 style="color: #add8e6">Session</h1>
    <table id="groupsTable" class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Group</th>
            <th style="width: 10em">Check</th>
        </tr>
        </thead>
        <tbody>
        <!-- Populated by JS -->
        </tbody>
    </table>
</div>
</body>
</html>