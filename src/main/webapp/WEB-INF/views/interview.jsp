<%--suppress ALL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Interview</title>
    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="../../resources/jquery/jquery-ui/jquery-ui.css"/>
    <link rel="stylesheet" href="../../resources/css/interview.css"/>
    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="../../resources/jquery/jquery-ui/jquery-ui.js"></script>
    <script src="../../resources/js/interview.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
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
                <li><a href="#">Home</a></li>
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
        </div>
    </div>
</nav>
<div class="custom-left">
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Candidate</h3>
        </div>
        <div class="panel-body">
            <div id="candidate-lastname">${interview.candidate.firstName}</div>
            <div id="candidate-firstname">${interview.candidate.lastName}</div>
            <div id="candidate-cv"><a href="#">CV</a></div>
        </div>
    </div>
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Interviewer</h3>
        </div>
        <div class="panel-body">
            <div id="interviewer-lastname">${interview.interviewer.lastName}</div>
            <div id="interviewer-firstname">${interview.interviewer.firstName}</div>
        </div>
    </div>
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Group</h3>
        </div>
        <div class="panel-body">
            <div id="group-name">Java-DP-092</div>
        </div>
    </div>
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Template</h3>
        </div>
        <div class="panel-body">
            <input name="template" form="interview" type="hidden" id="template-id" value="${template_id}">
            <div id="template-name">
                <img src="../../resources/images/icons/preload_xs.gif" />
            </div>
        </div>
    </div>
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Interview</h3>
        </div>
        <div class="panel-body" style="text-align: center">
            <div id="tm1" style="margin-bottom: 10px">0:00</div>
            <button type="submit" form="interview" class="btn btn-primary">Finish</button>
        </div>
    </div>
</div>
<div class="custom-main">
    <table id="question-table" class="table table-bordered custom-table">
        <thead>
        <tr>
            <th width="45%">Question</th>
            <th width="40%">Knowledge</th>
            <th width="10%">Comment</th>
            <th width="5%">Skipped</th>
        </tr>
        </thead>
        <tbody>
            <tr class="loading">
                <td colspan="4"></td>
            </tr>
        </tbody>
    </table>
</div>
<div id="dialog-form" title="Add comment">
    <form>
        <textarea id="text-question-comment" class="text ui-widget-content ui-corner-all"></textarea>
        <button type="submit" class="btn btn-primary" tabindex="-1" style="position:absolute; top:-1000px">Add Comment</button>
    </form>
</div>
<div class="custom-comments">
    <textarea name="comments" readonly form="interview"></textarea>
    <input type="text" id="text-common-comment" />
    <button id="add-common-comment" type="submit" class="btn btn-primary">Add Comment</button>
</div>
<form id="interview" action="/interview/save/${interview.id}" method="post"></form>
</body>
</html>