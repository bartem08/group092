<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                        $("#interviewesTable > tbody").append(
                                '<tr>' +
                                '<td><a href="/web/interviewers/' + interviewer.id + '"></a>' + interviewer.lastName + '</td>' +
                                '<td>' + interviewer.firstName + '</td>' +
                                '<td><a href="/web/interviewers/' + interviewer.id + '">&gt;</a></td>' +
                                '</tr>'
                        );
                    });
                }
            });
        });

        function deleteQuestion(id){
                $.ajax({
                    type: "DELETE",
                    url: "/rest/interviewers/" + id,
                            success: function() {
                            console.log("/rest/interviewers/" + id);
                        },
                    error: function(result) {
                        console.log(result);
                    }
                });
        }
    </script>

</head>
<body>
<div class="container" style="padding-top: 5em">

    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/web/logout">Log Out</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <h1 style="color: #add8e6">Interviewers</h1>
    <table id="interviewesTable" class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Last Name</th>
            <th>First Name</th>
        </tr>
        </thead>
        <tbody>
        <!-- Populated by JS -->
        </tbody>
    </table>
</div>
</body>
</html>
