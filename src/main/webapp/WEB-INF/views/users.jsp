<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/adduser" type="button" class="btn btn-default">Add user</a> <br /><br />

    <p>
        Show:
        <select id="table-filter">
            <option value="">All</option>
            <option value="ROLE_ADMIN">Admin</option>
            <option value="ROLE_BOSS">Boss</option>
            <option value="ROLE_MANAGER">Manager</option>
            <option value="ROLE_TEACHER">Teacher</option>
            <option value="ROLE_STUDENT">Student</option>
        </select>
    </p>

    <table class="table table-striped" id="usersTable">
        <thead>
            <tr>
                <th>Action</th>
                <th>Id</th>
                <th>Name</th>
                <th>Roles</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><a href="${contextPath}/adduser?id=${user.id}">Edit</a> | <a href="#">Delete</a></td>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>
                <c:forEach var="role" items="${user.roles}">
                    ${role.name}<br />
                </c:forEach>
                </td>

            </tr>
        </c:forEach>
        </tbody>
        </table>

</div>

<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">

<script>

    $(document).ready(function (){
        var table = $('#usersTable').DataTable({
            dom: 'lrtip'
        });

        $('#table-filter').on('change', function(){
            table.search(this.value).draw();
        });
    });

    $(function(){
        $("#usersTable").dataTable();
    })

</script>
<%@ include file = "footer.jsp"%>