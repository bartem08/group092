<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="../../../resources/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../../resources/css/input.css"/>
    <link rel="stylesheet" href="../../../resources/css/template.css"/>

    <script src="../../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../../resources/bootstrap/js/bootstrap.min.js"></script>

    <%--<script src="../../../resources/js/interviewerProfile.js"></script>--%>
    <script src="../../../resources/js/headerPopulator.js"></script>
    <script src="../../../resources/js/template.js"></script>
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
                <li><a href="/web/profile">Profile</a></li>
                <li><a href="/login">Log Out</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

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
                            <ul class="dropdown-menu selectTemplate">
                                <li><a href="#">New template</a></li>
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
                    <label for="maxValue" class="col-lg-2 control-label">Maximum value</label>
                    <div class="col-lg-10">
                        <select class="form-control" id="maxValue" style="max-width: 70px">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                            <option>10</option>
                        </select>
                        <br>
                        <a href="#" class="btn btn-default" id="addQuestion">Add question</a>
                    </div>
                    <br><br>
                    <div style="padding-top: 5em">

                    </div>
                </div>

            </fieldset>
        </form>

        <%--        <legend style="padding-top: 1em; padding-left: 7em">Templates</legend>
                <table class="table table-striped table-hover " id="templatesTable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Existing Templates</th>

                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Template NO1</td>


                    </tr>
                    </tbody>
                </table>--%>
    </div>
    <div class="right" style="padding-top: 5em; padding-right: 2em">
        <input hidden id="activeTemplateId" value="">
        <div>
            <legend style="float: left">Active template : <span id="activeTemplate"></span></legend>
        </div>

        <table class="table table-striped table-hover" id="templateQuestions">
            <thead>
            <tr>

                <th>Questions</th>

            </tr>
            </thead>
            <tbody id="tbodyId">
            <tr>
                <td>Question NO1</td>
            </tr>
            <tr>
                <td>Question NO2</td>
            </tr>
            <tr>
                <td>Question NO3</td>
            </tr>
            <tr>
                <td>Question NO4</td>
            </tr>


            </tr>
            </tbody>
        </table>


    </div>
</div>
</body>
</html>