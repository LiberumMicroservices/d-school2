<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/addmanager" type="button" class="btn btn-default">Add manager</a> <br /><br />

    <table class="table table-striped" id="usersTable">
        <thead>
        <tr>
            <th>Name</th>
            <th>E-mail</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${managers}">
            <tr>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <%--сделать проверку по конкретной школе а не общий блок--%>
                <td><a href="${contextPath}/userdetails?id=${user.id}">Details</a> | <a href="${contextPath}/userdetails?id=${user.id}#edit">Edit</a> | <a href="#">${user.enabled ? "Block" : "Unblock"}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

<%@ include file = "footer.jsp"%>