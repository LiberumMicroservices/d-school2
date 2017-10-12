<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <div class="container">

        <form:form method="POST" modelAttribute="responsibleForm" class="form-signin">
            <h4 class="form-signin-heading">${student.username}`s responsible person</h4>
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

            <spring:bind path="phone1">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="phone1" class="form-control" placeholder="Primary phone number"
                                autofocus="true"></form:input>
                    <form:errors path="phone1"></form:errors>
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
                    <label for="birthday">Birthday</label>
                    <form:input type="date" path="birthday" class="form-control" autofocus="true"></form:input>
                    <form:errors path="birthday"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            <a href="${contextPath}/studentdetails?id=${student.id}" type="button" class="btn btn-lg btn-danger btn-block">Cancel</a>
        </form:form>

    </div>

</div>

<%@ include file = "footer.jsp"%>