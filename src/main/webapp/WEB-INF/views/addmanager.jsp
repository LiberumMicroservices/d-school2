<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <div class="container">

        <form:form method="POST" modelAttribute="userForm" class="form-signin">
            <h2 class="form-signin-heading">New manager</h2>
            <spring:bind path="username">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <span>${message}</span>
                    <form:input type="text" path="username" class="form-control" placeholder="Username"
                                autofocus="true"></form:input>
                    <form:errors path="username"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
                    <form:errors path="password"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="confirmPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="confirmPassword" class="form-control"
                                placeholder="Confirm password"></form:input>
                    <form:errors path="confirmPassword"></form:errors>
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

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

    </div>

</div>

<%@ include file = "footer.jsp"%>