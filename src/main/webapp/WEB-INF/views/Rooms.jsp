<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/addroom" type="button" class="btn btn-default">Add room</a> <br /><br />

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>Phone</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="room" items="${rooms}">
            <tr>
                <td>${room.name}</td>
                <td>${room.address}</td>
                <td>${room.phone1}</td>
                <td>${room.description}</td>
                <td><a href="${contextPath}/roomdetails?id=${room.id}">Details</a> | <a href="#">${room.enabled ? "Block" : "Unblock"}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<%@ include file = "footer.jsp"%>