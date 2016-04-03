<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interview Login Page</title>
</head>
<center>
    <body>
    <form action="/web/login" method="POST">
        <table>
            <tr>
                <td>User Name:</td>
                <td><input type="text" name="username" /></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td colspan="2"><input name="submit" type="submit" value="Sign In" /></td>
            </tr>
        </table>
    </form>
    </body>
</center>
</html>