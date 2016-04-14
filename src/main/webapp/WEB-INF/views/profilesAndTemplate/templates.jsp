<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="../../../resources/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../../resources/css/input.css"/>
    <link rel="stylesheet" href="../../../resources/css/template.css"/>
    <link rel="stylesheet" href="../../../resources/jquery/jquery-ui/jquery-ui.css">

    <script src="../../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../../resources/jquery/jquery-ui/jquery-ui.js"></script>
    <script src="../../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="../../../resources/js/header.js"></script>
    <script src="../../../resources/js/templates.js"></script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">Schedule<span class="caret"></span></a>
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
<input id="editQuestionId" type="hidden" value="">
<input id="activeTemplateId" type="hidden" value="">

<div class="myContainer" style="padding-top: 3em">
    <div class="left">
        <form class="form-horizontal" style="padding-top: 5em">
            <fieldset>
                <legend style="padding-left: 7em">Add a question to template</legend>
                <div class="form-group">
                    <ul class="nav nav-pills">
                        <label for="dropdown" class="col-lg-2 control-label">Choose a template</label>
                        <li id="dropdown" class="dropdown" style="padding-left: 1em">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Templates <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu selectedTemplates">
                                <li><a href="#" onclick="addNewTemplate()">New template</a></li>
                                <li class="divider"></li>
                            </ul>
                        </li>
                    </ul>
                    <br>
                    <label for="textArea" class="col-lg-2 control-label">Enter your question</label>
                    <div class="col-lg-10">
                        <textarea class="form-control" rows="3" id="textArea"
                                  style="width: 100%; max-width: 100%"></textarea>
                        <span class="help-block"></span>
                    </div>
                    <label for="maxValue" class="col-lg-2 control-label">Maximum value: </label>

                    <div class="col-lg-10">
                        <br>
                        <span type="text" id="maxValue" style="font-weight:bold"></span>
                        <br>
                        <br>
                        <div id="slider"></div>
                        <br>
                        <a href="#" class="btn btn-default" id="addQuestion">Save question</a>
                    </div>
                    <br><br>
                </div>
            </fieldset>
        </form>
    </div>

    <div class="right" style="padding-top: 5em; padding-right: 2em">
        <div>
            <legend style="float: left">Active template : <span id="activeTemplate"></span></legend>
        </div>
        <button class="btn btn-default" id="saveQuestionsOrder">Save questions order</button>
        <br><br>
        <table class="table table-striped table-hover" id="templateQuestions">
            <thead>
            <tr>
                <th>Questions</th>
            </tr>
            </thead>
            <tbody id="templateQuestionsBody" style="width: 80%">
                <!-- Populated by JS-->
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>