<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

<div class="container-fluid">

    <div class="container">

        <form:form method="POST" modelAttribute="schoolForm" class="form-signin">
            <h2 class="form-signin-heading">New school</h2>
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="name" class="form-control" placeholder="School name"
                                autofocus="true"></form:input>
                    <form:errors path="name"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="nameBrand">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="nameBrand" class="form-control" placeholder="School brand"
                                autofocus="true"></form:input>
                    <form:errors path="nameBrand"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="email" class="form-control" placeholder="E-Mail"
                                autofocus="true"></form:input>
                    <form:errors path="email"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="phone">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="phone" class="form-control" placeholder="Phone number"
                                autofocus="true"></form:input>
                    <form:errors path="phone"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="address">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="address" class="form-control" placeholder="Address"
                                autofocus="true"></form:input>
                    <form:errors path="address"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="description">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:textarea type="text" rows="4" path="description" class="form-control" placeholder="Description"
                                autofocus="true"></form:textarea >
                    <form:errors path="description"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

    </div>

</div>

<%@ include file = "footer.jsp"%>