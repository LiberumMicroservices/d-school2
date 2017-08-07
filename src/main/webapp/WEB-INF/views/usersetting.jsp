<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <h4>User Settings</h4>

    <div class="panel-group">

        <div class="panel panel-info">
            <div class="panel-heading">User Info</div>
            <div class="panel-body">
                Name: ${user.username} <br />
                E-mail: ${user.email} <br />

                <br />
                <table id="schoolAccounts" class="table table-striped" style="width: auto; ">
                    <thead>
                    <tr>
                        <th>School</th>
                        <th>Role</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="scoolAccount" items="${user.schoolAccounts}">
                        <tr>
                            <td>${scoolAccount.school.name}</td>
                            <td>${scoolAccount.role.name}  </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">Change school</div>
            <div class="panel-body">
                <p class="text-danger">You will be logged out after the operation!</p>
                <form:form method="POST" modelAttribute="userSetting" class="form col-sm-3">
                    <span>${message}</span>

                    <spring:bind path="currentSchool">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:select path="currentSchool" multiple="false" class="form-control" autofocus="true">
                                <form:options items="${schoolList}" />
                            </form:select>
                            <form:errors path="currentSchool"></form:errors>
                        </div>
                    </spring:bind>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                </form:form>
                <br />
            </div>
        </div>
    </div>

</div>

<%@ include file = "footer.jsp"%>