<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interviewer Login Page</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

</head>
<center>
    <body>
    <div class="container" style="margin-top:40px">
        <div class="row">
            <div class="col-sm-6 col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <strong><h2 class="form-signin-heading">Please sign in...</h2></strong>
                    </div>
                    <div class="panel-body">
                        <form class="form-signin" action="/web/login" method="POST">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">
                                    <p>
                                        Your fake login attempt was bursted, dare again !!<br />
                                        Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                                    </p>
                                </div>
                            </c:if>
                            <c:if test="${not empty logout}">
                                <div class="alert alert-success">
                                    <p>You have been logged out successfully.</p>
                                </div>
                            </c:if>
                            <fieldset>
                                <div class="row">
                                    <div class="center-block">
                                        <img class="profile-img"
                                             src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120" alt="">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12 col-md-10  col-md-offset-1 ">
                                        <div class="form-group">
                                            <div class="input-group">
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-user"></i>
												</span>
                                                <input class="form-control" type="text" id="username" name="username" placeholder="Enter your login" required autofocus/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="input-group">
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-lock"></i>
												</span>
                                                <input class="form-control" type="password" id="password" name="password" placeholder="Enter your password" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="checkbox">
                                                <label>
                                                    <input class="checkbox" id="remember_me" name="remember_me" type="checkbox"/> Remember me
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Sign In"/>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</center>
</html>