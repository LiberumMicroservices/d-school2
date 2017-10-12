<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${requestScope.user}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <div class="panel panel-info" id="viewUser">
        <div class="panel-heading"><p class="text-info">
            <span>${message}</span>
            <b>${user.username}</b></p></div>
        <div class="panel-body">
            E-mail: ${user.email}<br />
            Phone 1: ${user.phone1}<br />
            Phone 2: ${user.phone2}<br />
            Skype: ${user.skype}<br />
            Address: ${user.address}<br />
            Birthday: ${user.birthday}<br />
            Description: ${user.description}<br />
            <br />
            <a href="#" id="edit">Edit</a> <br /><br />
        </div>
    </div>

    <div class="panel panel-info" id="editUser" style="display:none;">
        <div class="panel-heading"><p class="text-info">
            <span>${message}</span>
            <b>${user.username}</b></p>
            <p class="text-danger">Эти поля не проверяются на корректность</p>
        </div>
        <div class="panel-body">
            <form:form method="POST" modelAttribute="userForm" class="form-signin" cssStyle="margin: 10px">
                <h3 class="form-signin-heading">Edit</h3>
                <span>${message}</span>
                <spring:bind path="id">
                    <div class="form-group ${status.error ? 'has-error' : ''}" style="display:none;">
                        <form:input path="id"></form:input>
                        <form:errors path="id"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="username">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
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

                <spring:bind path="phone1">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="phone1" class="form-control" placeholder="Primary phone number"
                                    autofocus="true"></form:input>
                        <form:errors path="phone1"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="phone2">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="phone2" class="form-control" placeholder="Secondary phone number (optionaly)"
                                    autofocus="true"></form:input>
                        <form:errors path="phone2"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="skype">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="skype" class="form-control" placeholder="Skype (optionaly)"
                                    autofocus="true"></form:input>
                        <form:errors path="skype"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="address">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="address" class="form-control" placeholder="Address (optionaly)"
                                    autofocus="true"></form:input>
                        <form:errors path="address"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="description">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:textarea type="text" rows="4" path="description" class="form-control" placeholder="Description (optionaly)"
                                       autofocus="true"></form:textarea >
                        <form:errors path="description"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="birthday">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="date" path="birthday" class="form-control" autofocus="true"></form:input>
                        <form:errors path="birthday"></form:errors>
                    </div>
                </spring:bind>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button><br />
                <a href="#" type="button" class="btn btn-lg btn-danger btn-block" id="cancel">Cancel</a>
            </form:form>

        </div>
    </div>

</div>

    <script>
        $(function(){
            $("#edit").click(function(){
                $("#viewUser").hide();
                $("#editUser").show();
            });

            $("#cancel").click(function(){
                $("#editUser").hide();
                $("#viewUser").show();
            });
            if(window.location.hash == "#edit"){
                $("#viewUser").hide();
                $("#editUser").show();
            }
        });
    </script>

<%@ include file = "footer.jsp"%>