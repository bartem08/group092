<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Interviewer Updating Page</title>

    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css"/>

    <script src="/resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {
            var interviewerId = $("#interviewerId").val();
            $.ajax({
                type: "GET",
                url: "/rest/interviewers/"+ interviewerId,
                success: function(result) {
                    var interviewer = JSON.stringify(result);
                    console.log("interviewer JSON: " + interviewer);
                    interviewer = JSON.parse(interviewer);
                    var lastName = interviewer.lastName;
                    var firstName = interviewer.firstName;
                    var email = interviewer.email;
                    var skype = interviewer.skype;
                    var phone = interviewer.phone;
                    var login = interviewer.login;
                    var password = interviewer.password;
                    var role = interviewer.role;

                    $("#lastName").val(lastName);
                    $("#firstName").val(firstName);
                    $("#email").val(email);
                    $("#skype").val(skype);
                    $("#phone").val(phone);
                    $("#login").val(login);
                    $("#password").val(password);
                    $("#role").val(role);
                }
            });

            $("#save").click(function() {
                console.log("Button save function fired");
                var interviewer = {
                    lastName : $("#lastName").val(),
                    firstName : $("#firstName").val(),
                    email : $("#email").val(),
                    skype : $("#skype").val(),
                    phone : $("#phone").val(),
                    login : $("#login").val(),
                    password : $("#password").val(),
                    role : $("#role").val()
                };
                $.ajax({
                    type: "PUT",
                    data: JSON.stringify(interviewer),
                    contentType: "application/json;",
                    url: "/rest/interviewers/"+interviewerId,
                }).done(function () {
                    location.href="/web/admin/interviewers/"+interviewerId;
                    console.log("/rest/interviewers/" + interviewerId);
                }).fail(function () {
                    console.log("fail save");
                })
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

    <strong><h1>Edit Interviewer</h1></strong>

    <form role="form-horizontal" id="interviewerForm">
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
        <div id = "save">
            <a href="#" class="btn btn-default">Submit</a>
        </div>
    </form>
</div>
</body>
</html>