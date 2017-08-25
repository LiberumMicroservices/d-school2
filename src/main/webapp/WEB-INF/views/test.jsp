<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${requestScope.user}"/>

<%@ include file = "header.jsp"%>

<form:form method="POST" modelAttribute="userForm" class="form-signin" cssStyle="margin: 10px">
    <span>${message}</span>
    <spring:bind path="user.username">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="user.username" class="form-control" placeholder="Username"
                        autofocus="true"></form:input>
            <form:errors path="user.username"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="user.email">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="user.email" class="form-control" placeholder="Username"
                        autofocus="true"></form:input>
            <form:errors path="user.email"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="user.address">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="user.address" class="form-control" placeholder="Addres"
                        autofocus="true"></form:input>
            <form:errors path="user.address"></form:errors>
        </div>
    </spring:bind>

    <h3> Responsible persons</h3>

    <c:forEach items="${userForm.responsiblePersons}" var="contact" varStatus="status">
        <h6> Person ${status.index}</h6>
        <table class="table">
            <tr>
                <td>
                    <input class="form-control" style="font-size: small" id="contacts[${status.index}].username" value="${contact.username}"/>
                </td>
                <td>
                    <input class="form-control" style="font-size: small" id="contacts[${status.index}].email" value="${contact.email}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input class="form-control" style="font-size: small" id="contacts[${status.index}].skype" value="${contact.skype}"/>
                </td>
                <td>
                    <input class="form-control" style="font-size: small" id="contacts[${status.index}].phone1" value="${contact.phone1}"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input class="form-control" style="font-size: small" id="contacts[${status.index}].address" value="${contact.address}"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input class="form-control" style="font-size: small" id="contacts[${status.index}].description" value="${contact.description}"/>
                </td>
            </tr>
        </table>

    </c:forEach>

    <%--<c:forEach items="${userForm.responsiblePersons}" var="contact" varStatus="status">--%>
        <%--<h6> Person ${status.index}</h6>--%>
        <%--<table class="table">--%>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--<input class="form-control" style="font-size: small" name="contacts[${status.index}].username" value="${contact.username}"/>--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input class="form-control" style="font-size: small" name="contacts[${status.index}].email" value="${contact.email}"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--<input class="form-control" style="font-size: small" name="contacts[${status.index}].skype" value="${contact.skype}"/>--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input class="form-control" style="font-size: small" name="contacts[${status.index}].phone1" value="${contact.phone1}"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="2">--%>
                    <%--<input class="form-control" style="font-size: small" name="contacts[${status.index}].address" value="${contact.address}"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="2">--%>
                    <%--<input class="form-control" style="font-size: small" name="contacts[${status.index}].description" value="${contact.description}"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>

    <%--</c:forEach>--%>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>

</form:form>

<%@ include file = "footer.jsp"%>