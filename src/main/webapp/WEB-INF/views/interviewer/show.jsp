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
            var interviewerId = $("#interviewerId").val();
            $.ajax({
                type: "GET",
                url: "/rest/interviewers/" + interviewerId,
                success: function(result) {
                    var interviewer = JSON.stringify(result);
                    console.log("JSON: " + interviewer);
                    interviewer = JSON.parse(interviewer);
                    $("#interviewerTable").append(
                            '<tr>' +
                            '<td>' + 'Last Name' + '</td>' +
                            '<td>' + interviewer.lastName + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'First Name' + '</td>' +
                            '<td>' + interviewer.firstName + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'E-mail' + '</td>' +
                            '<td>' + interviewer.email + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'Skype' + '</td>' +
                            '<td>' + interviewer.skype + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'Phone Number' + '</td>' +
                            '<td>' + interviewer.phone + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'Login' + '</td>' +
                            '<td>' + interviewer.login + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'Password' + '</td>' +
                            '<td>' + interviewer.password + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td>' + 'Role' + '</td>' +
                            '<td>' + interviewer.role + '</td>' +
                            '</tr>'+
                            '<tr>' +
                            '<td><a class="active" href="/web/admin/interviewers/' + interviewer.id + '?form">' + 'Edit interviewer' + '</a>' +
                            '<span class="glyphicon glyphicon-edit"></span></td>'+
                            '</tr>'
                    );
                }
            });
        });
    </script>

</head>
<body>

<input id="interviewerId" type="hidden" value="${interviewerId}"/>

<div class="container" style="padding-top: 5em">

    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/web/admin/interviewers">Home</a></li>
                    <li><a href="/web/logout">Log Out</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <h1 style="color: #add8e6">Interviewer</h1>

    <table id="interviewerTable" class="table table-striped table-hover">
        <tbody>

        </tbody>
    </table>
</div>
</body>
</html>