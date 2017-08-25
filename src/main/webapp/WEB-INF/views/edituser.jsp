<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <div class="panel-group">

        <div class="panel panel-info">
            <div class="panel-heading">Edit user</div>
            <div class="panel-body">

        <form:form method="POST" modelAttribute="userForm" class="form col-sm-3">
            <spring:bind path="username">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <span>${message}</span>
                    <form:input type="text" path="username" class="form-control" placeholder="Username"
                                autofocus="true"></form:input>
                    <form:errors path="username"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="email" class="form-control" placeholder="E-Mail"
                                autofocus="true"></form:input>
                    <form:errors path="email"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="role">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:select path="role" multiple="false" class="form-control" autofocus="true">
                            <form:options items="${rolesadm}" />
                    </form:select>
                    <form:errors path="role"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">Schools:</div>
            <div class="panel-body">

            <table id="schoolAccounts" class="table table-striped" style="width: auto; ">
                <thead>
                <tr>
                    <th>School</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="scoolAccount" items="${user.schoolAccounts}">
                    <tr>
                        <td>${scoolAccount.school.name}</td>
                        <td>${scoolAccount.role.name}
                            <%--<form:select id="${scoolAccount.id}" path="${scoolAccount.role.name}" multiple="false" autofocus="true">--%>
                                <%--<form:options items="${roles}" />--%>
                            <%--</form:select>--%>
                        </td>
                        <td><a href="${contextPath}/edituser?id=${user.id}&action=ban&schoolAccountId=${scoolAccount.id}&enabled=${!scoolAccount.enabled}">${scoolAccount.enabled ? "Block" : "Unblock"}</a> | <a href="#">Set</a> <br /></td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>

            <h3>Add School</h3>
                <form:select id="newSchool" path="schoolName" multiple="false" autofocus="true">
                    <form:options items="${schools}" />
                </form:select>

                <form:select id="newRole" path="roleName" multiple="false" autofocus="true">
                    <form:options items="${roles}" />
                </form:select>

                <a href="#" onclick="add()" >Add</a>

                <script>
                    function add(){
                        var school = $("#newSchool option:selected").text();
                        var role = $("#newRole option:selected").text();
                        location.href = "${contextPath}/edituser?id=${user.id}&action=addSchool&school=" + school + "&role=" + role;
                    }
                </script>
            </div>
        </div>

    </div>

</div>

<%@ include file = "footer.jsp"%>