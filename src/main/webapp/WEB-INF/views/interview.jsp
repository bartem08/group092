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
    <script src="../../resources/js/header.js"></script>
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
                    <ul id="hdrDropdownSchedule" class="dropdown-menu"></ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/web/interviewer">Profile</a></li>
                <li><a href="/web/logout">Log Out</a></li>
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
            <div id="candidate-lastname"></div>
            <div id="candidate-firstname"></div>
            <div id="candidate-cv"><a href="#">CV</a></div>
        </div>
    </div>
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Interviewer</h3>
        </div>
        <div class="panel-body">
            <div id="interviewer-lastname"></div>
            <div id="interviewer-firstname"></div>
        </div>
    </div>
    <div id="group" class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Group</h3>
        </div>
        <div class="panel-body">
            <div id="group-name">
                <input form="interview" type="hidden" name="info" value="${group_name}"/>
                ${group_name}
            </div>
        </div>
    </div>
    <div id="template" class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Template</h3>
        </div>
        <div class="panel-body">
            <div id="template-name">
                <input form="interview" type="hidden" name="info" value="${template_name}"/>
                ${template_name}
            </div>
        </div>
    </div>
    <div class="panel panel-primary info-panel">
        <div class="panel-heading p-h">
            <h3 class="panel-title">Interview</h3>
        </div>
        <div class="panel-body" style="text-align: center">
            <div id="tm1" style="margin-bottom: 10px">0:00</div>
            <div id="res" style="margin-bottom: 10px"></div>
            <button id="f_finish" type="submit" form="interview" class="btn btn-primary">Finish</button>
        </div>
    </div>
</div>
<div class="custom-main">
    <div id="question-table">
        <table class="table table-bordered custom-table">
            <tbody>
            <tr class="loading">
                <td colspan="4"></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="custom-comments">
        <textarea name="comments" form="interview" placeholder="Comments..."></textarea>
    </div>
    <div class="custom-result">
        <div class="result"></div>
        <div class="progress">
            <div class="progress-bar progress-bar-success custom-bar" role="progressbar" aria-valuemin="0" ><span class="sr-only">40% Complete (success)</span></div>
        </div>
    </div>
</div>
<div id="dialog-form" title="Add comment">
    <form>
        <textarea id="text-question-comment" class="text ui-widget-content ui-corner-all"></textarea>
        <button type="submit" class="btn btn-primary" tabindex="-1" style="position:absolute; top:-1000px">Add Comment</button>
    </form>
</div>

<form id="interview" action="/interview/save" method="post">
    <input type="hidden" value="${interview_id}" id="int_id" name="info" />
</form>
</body>
</html>