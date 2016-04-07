<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interviewer Appending Page</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>

        $(document).ready(function () {
            $("#submit").click(function () {
                var interviewer = new Object();
                interviewer.lastName = $('#lastName').val();
                interviewer.firstName = $('#firstName').val();
                interviewer.email = $('#email').val();
                interviewer.skype = $('#skype').val();
                interviewer.phone = $('#phone').val();
                interviewer.login = $('#login').val();
                interviewer.password = $('#password').val();
                interviewer.role = $('#role').val();
                $.ajax({
                    type: "POST",
                    url: "/rest/interviewers",
                    dataType: 'json',
                    data:interviewer,
                    beforeSend: function(interviewer) {
                        form.find('input[type="submit"]').attr('disabled', 'disabled');
                    },
                    success: function(interviewer){
                        if (interviewer['error']) {
                            alert(interviewer['error']);
                        } else {
                            alert("Data has been sent!");
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    },
                    complete: function(interviewer) {
                        form.find('input[type="submit"]').prop('disabled', false);
                    }
                });
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
                    <li><a href="/web/admin/interviewers">Home</a></li>
                    <li><a href="/web/logout">Log Out</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <strong><h1>Add Interviewer</h1></strong>

    <form role="form-horizontal" method="post" action="" id="interviewerForm">
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input class="form-control" type="text" id="lastName" name="lastName" placeholder="Enter last name" val="">
        </div>
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input class="form-control" type="text" id="firstName" name="firstName" placeholder="Enter first name" val="">
        </div>
        <div class="form-group">
            <label for="email">E-mail</label>
            <input class="form-control" type="text" id="email" name="email" placeholder="Enter email" val="">
        </div>
        <div class="form-group">
            <label for="skype">Skype</label>
            <input class="form-control" type="text" id="skype" name="skype" placeholder="Enter skype" val="">
        </div>
        <div class="form-group">
            <label for="phone">Phone Number</label>
            <input class="form-control" type="text" id="phone" name="phone" placeholder="Enter phone" val="">
        </div>
        <div class="form-group">
            <label for="login">Login</label>
            <input class="form-control" type="text" id="login" name="login" placeholder="Enter login" val="">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input class="form-control" type="text" id="password" name="password" placeholder="Enter password" val="">
        </div>
        <div class="form-group">
            <label for="role">Role</label>
            <input class="form-control" type="text" id="role" name="role" placeholder="Enter role" val="">
        </div>
        <div class="form-group">
                <input class="btn btn-lg btn-primary btn-block" id="submit" type="submit" value="Submit"/>
        </div>
    </form>
</div>
</body>
</html>