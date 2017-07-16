<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>


    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
        </div>

    </form>

<div class="container-fluid">
    <h4 >В системе имеется несколько уровней доступа:</h4>
    <div style="margin: 15px;">
        <p class="text-info"><b>Admin</b> - администратор системы</p>
        <p class="text-warning">login - admin<br />
            password - admin</p>
        Полномочия:
        <ul>
            <li>Добавление школы</li>
            <li>Редактирование школы</li>
            <li>Блокировка школы</li>
            <li>Удаление школы</li>
            <li>Добавление директора школы</li>
            <li>Блокировка директора</li>
            <li>Удаление директора</li>
        </ul>
    </div>
    <br />
    <div style="margin: 15px;">
        <p class="text-info"><b>Boss</b> - Директор школы</p>
        <p class="text-warning">login - boss<br />
            password - boss</p>
        Полномочия:
        <ul>
            <li>Добавление менеджеров и преподавателей</li>
            <li>Редактирование менеджеров и преподавателей</li>
            <li>Блокировка менеджеров и преподавателей</li>
            <li>Удаление менеджеров и преподавателей</li>
            <li>Добавление, удаление, редактирование залов</li>
            <li>Создание, редактирование и удаление курсов</li>
        </ul>
    </div>

</div>

<%@ include file = "footer.jsp"%>