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
    <div class="container">
        <div id="login">
            <h2 class="form-signin-heading">Please sign in...</h2>
            <form class="form-signin" action="/web/login" method="POST">
                <table>
                    <tr>
                        <td>User Name:</td>
                        <td><input class="form-control" type="text" name="username" placeholder="Login" required autofocus/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input class="form-control" type="password" name="password" placeholder="Password" /></td>
                    </tr>
                    <tr>
                        <td>Remember me</td>
                        <td>
                            <input class="checkbox" id="remember_me" name="remember_me" type="checkbox"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Sign In"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    </body>
</center>
</html>