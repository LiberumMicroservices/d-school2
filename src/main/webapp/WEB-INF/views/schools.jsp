<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/addschool" type="button" class="btn btn-default">Add school</a> <br /><br />

    <table class="table table-striped" id="schoolsTable">
        <thead>
        <tr>
            <th>Name</th>
            <th>Brand</th>
            <th>Address</th>
            <th>Phone</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="school" items="${schools}">
            <tr>
                <td>${school.name}</td>
                <td>${school.nameBrand}</td>
                <td>${school.address}</td>
                <td>${school.phone}</td>
                <td>${school.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<script>
$(function(){
    $("#schoolsTable").dataTable();
})
</script>

<%@ include file = "footer.jsp"%>