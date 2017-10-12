<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <a href="${contextPath}/rooms" type="button" class="btn btn-default">Rooms</a> <br /><br />

    <div class="container">

        <form:form method="POST" modelAttribute="roomForm" class="form-signin">
            <h3 class="form-signin-heading">New room</h3>
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <span>${message}</span>
                    <form:input type="text" path="name" class="form-control" placeholder="Name"
                                autofocus="true"></form:input>
                    <form:errors path="name"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="address">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="address" class="form-control" placeholder="Address"
                                autofocus="true"></form:input>
                    <form:errors path="address"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="phone1">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="phone1" class="form-control" placeholder="Phone number (optionaly)"
                                autofocus="true"></form:input>
                    <form:errors path="phone1"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="description">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:textarea type="text" rows="4" path="description" class="form-control" placeholder="Description (optionaly)"
                                   autofocus="true"></form:textarea >
                    <form:errors path="description"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            <a href="${contextPath}/rooms" type="button" class="btn btn-lg btn-danger btn-block">Cancel</a>
        </form:form>

    </div>

</div>

<%@ include file = "footer.jsp"%>