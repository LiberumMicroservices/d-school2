<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html >
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="${contextPath}/resources/img/female.png">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${contextPath}">${currentSchool}</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${contextPath}">Home</a></li>
            <sec:authorize access="isAuthenticated()">
                <li><a href="#">Lessons</a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER')">
                <li><a href="${contextPath}/students">Students</a></li>
                <li><a href="${contextPath}/teachers">Teachers</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_BOSS')">
                <li><a href="${contextPath}/managers">Managers</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${contextPath}/users">Users</a></li>
                        <li><a href="${contextPath}/schools">Schools</a></li>
                        <li><a href="#">Setting</a></li>
                    </ul>
                </li>
            </sec:authorize>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <sec:authorize access="!isAuthenticated()">
                <%--<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>--%>
                <li><a href="<c:url value="/login" />"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li><a href="${contextPath}/usersetting"><span class="glyphicon glyphicon-user"></span> <sec:authentication property="principal.username" /></a></li>
                <li><a href="<c:url value="/logout" />"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
            </sec:authorize>
        </ul>
    </div>
</nav>