<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Admin Console</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {
            $.ajax({
                type: "GET",
                url: "/rest/interviewers",
                success: function(result) {
                    var interviewers = JSON.stringify(result);
                    console.log("JSON: " + interviewers);
                    interviewers = JSON.parse(interviewers);

                    $.each(interviewers, function(i, interviewer) {
                        $("#interviewersTable").append(
                                '<tr>' +
                                '<td><a class="active" href="/web/admin/interviewers/' + interviewer.id + '">' + interviewer.lastName + '</a>' +
                                '<span class="glyphicon glyphicon-edit"></span></td>' +
                                '<td>' + interviewer.firstName + '</td>' +
                                '<td><button id="'+interviewer.id+'" class="btn btn-danger btn-xs btn-delete">' +
                                '<span class="glyphicon glyphicon-floppy-remove"></span></button></td>' +
                                '</tr>'
                        );
                        $("#" + interviewer.id).click(function () {
                            $.ajax({
                                type: "DELETE",
                                url: "/rest/interviewers/" + interviewer.id,
                                success: function () {
                                    location.href="/web/admin/interviewers";
                                    console.log("success delete");
                                }
                            });
                        });
                    });
                }
            });
        });
    </script>

</head>
<body>
<div class="container" style="padding-top: 5em">

    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/web/groups">Access to account</a></li>
                    <li><a href="/web/logout">Log Out</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <h1 style="color: #add8e6">Interviewers</h1>

    <div class="left-container">
        <a href="/web/admin/interviewers?form"><span class="glyphicon glyphicon-plus-sign"></span>Add Interviewer</a>
    </div>

    <table id="interviewersTable" class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Last Name</th>
            <th>First Name</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>
</body>
</html>
