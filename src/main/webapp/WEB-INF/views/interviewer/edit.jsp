<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interviewer Updating Page</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {

            $("#interviewerForm").submit(function(){
                var form = $(this);
                var error = false;
                form.find('input, textarea').each( function(){
                    if ($(this).val() == '') {
                        alert('Fill in the field"'+$(this).attr('placeholder')+'"!');
                        error = true;
                    }
                });
                if (!error) {
                    var data = form.serialize();
                    $.ajax({
                        type: "POST",
                        url: "/rest/interviewers",
                        dataType: 'json',
                        data: data,
                        beforeSend: function(data) {
                            form.find('input[type="submit"]').attr('disabled', 'disabled');
                        },
                        success: function(data){
                            if (data['error']) {
                                alert(data['error']);
                            } else {
                                alert("Data has been sent!");
                            }
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            alert(xhr.status);
                            alert(thrownError);
                        },
                        complete: function(data) {
                            form.find('input[type="submit"]').prop('disabled', false);
                        }

                    });
                }
                return false;
            });

            var interviewerId = $("#interviewerId").val();
            $.ajax({
                type: "GET",
                url: "/rest/interviewers/" + interviewerId,
                success: function(result) {
                    var interviewer = JSON.stringify(result);
                    console.log("JSON: " + interviewer);
                    interviewer = JSON.parse(interviewer);
                    var id = interviewer.id;
                    $("#ID").val(id);
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
                    <li><a href="/web/interviewers">Home</a></li>
                    <li><a href="/web/logout">Log Out</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <strong><h1>Edit Interviewer</h1></strong>

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
            <div align="center">
                <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Submit"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>