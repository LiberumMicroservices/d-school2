<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/addcourse" type="button" class="btn btn-default">Add Course</a> <br /><br />

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Start</th>
            <th>End</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="course" items="${courses}">
            <tr>
                <td>${course.name}</td>
                <td>${course.startDate}</td>
                <td>${course.endDate}</td>
                <td>${course.description}</td>
                <td><a href="${contextPath}/coursedetails?id=${course.id}">Details</a> | <a href="#">${course.enabled ? "Block" : "Unblock"}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<%@ include file = "footer.jsp"%>