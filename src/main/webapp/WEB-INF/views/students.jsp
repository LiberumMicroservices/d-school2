<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/addstudent" type="button" class="btn btn-default">Add student</a> <br /><br />

    <table class="table table-striped" id="usersTable">
        <thead>
        <tr>
            <th>Name</th>
            <th>E-mail</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${students}">
            <tr>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td><a href="${contextPath}/studentdetails?id=${user.id}">Details</a> | <a href="#">${user.enabled ? "Block" : "Unblock"}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

<%@ include file = "footer.jsp"%>