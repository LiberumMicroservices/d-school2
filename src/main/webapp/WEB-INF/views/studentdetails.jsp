<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="message" value="${requestScope.message}"/>

<%@ include file = "header.jsp"%>

 <%--  Add editable  --%>
<link href="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>

<div class="container-fluid">
    <a href="${contextPath}/addstudent" type="button" class="btn btn-default">New student</a>
    <a href="${contextPath}/students" type="button" class="btn btn-default">All students</a>
    <br /><br />

    <div class="panel panel-info">
        <div class="panel-heading"><p class="text-info">
            <span>${message}</span>
            <a href="#" id="username" data-type="text" data-pk="${user.id}" data-title="Enter username" data-url="${contextPath}/editstudentvalue" class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                <b>${user.username}</b></a></p>
        </div>

        <div class="panel-body">
            <b>E-mail:</b> <a href="#" id="email" data-type="text" data-pk="${user.id}" data-title="Enter E-mail"
                              data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                              data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.email}</a><br />
            <b>Phone 1:</b><a href="#" id="phone1" data-type="text" data-pk="${user.id}" data-title="Enter phone 1"
                              data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                              data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.phone1}</a><br />
            <b>Phone 2:</b> <a href="#" id="phone2" data-type="text" data-pk="${user.id}" data-title="Enter phone 2"
                               data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                               data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.phone2}</a><br />
            <b>Skype:</b> <a href="#" id="skype" data-type="text" data-pk="${user.id}" data-title="Enter skype"
                             data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                             data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.skype}</a><br />
            <b>Address:</b> <a href="#" id="address" data-type="text" data-pk="${user.id}" data-title="Enter address"
                               data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                               data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.address}</a><br />
            <b>Description:</b> <a href="#" id="description" data-type="text" data-pk="${user.id}" data-title="Enter description"
                                   data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                                   data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.description}</a><br />
            <b>Birthday:</b> <a href="#" id="birthday" data-type="date" data-pk="${user.id}"
                                data-url="${contextPath}/editstudentvalue" class="editable editable-click"
                                data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                ${user.birthday}</a><br />

            <br />
            <b>Courses:</b>
            <table class="table table-hover table-fit">
                <c:forEach var="course" items="${user.courses}">
                    <tr>
                        <td><a href="${contextPath}/courseuser?user=${user.id}&course=${course.name}&action=del">
                            <span class="glyphicon glyphicon-remove" style="color:red"></span></a></td>
                        <td><a href="${contextPath}/coursedetails?id=${course.id}">${course.name}</a></td>
                    </tr>
                </c:forEach>
            </table>

            <div class="input-group">
                <select class="form-control" id="addCourse" style="width:auto;">
                    <c:forEach var="course" items="${freecourses}">
                        <option>${course.name}</option>
                    </c:forEach>
                </select>
                    <a href="#" class="btn btn-default" type="button" tabindex="-1" onclick="add()">add</a>
            </div>

            <script>

                function add(){
                    var course = $('#addCourse').val();
                    location.href = "${contextPath}/courseuser?user=${user.id}&course=" + course + "&action=add";
                }

                $(document).ready(function() {
                    //toggle `popup` / `inline` mode
                    $.fn.editable.defaults.mode = 'popup';

                    $('.editable').editable({

                        ajaxOptions: { dataType: 'json' },

                        placement: function (context, source) {
                            var popupWidth = 336;
                            if(($(window).scrollLeft() + popupWidth) > $(source).offset().left){
                                return "right";
                            } else {
                                return "left";
                            }
                        },
                        validate: function(value) {
                            if($.trim(value) == '') {
                                return 'This field is required';
                            }
                        },
                        success: function(response, newValue) {
                            if(!response.success) return response.msg;
                        }
                    });
                });
            </script>
        </div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading"><p class="text-info"><b>Responsible people</b>&emsp;
            <a href="${contextPath}/addresponsibleperson?id=${user.id}" type="button" class="btn btn-info btn-sm">
                <span class="glyphicon glyphicon glyphicon-plus"></span></a></p>
        </div>
        <div class="panel-body">

            <c:forEach var="person" items="${user.responsiblePersons}">
                <div style="border: 1px dashed #89c7ff; padding: 5px;">
                    <p class="text-info"><b>
                        <a href="#" id="${person.id}_username" data-type="text" data-pk="${person.id}"
                           data-title="Enter username" data-url="${contextPath}/editresponsiblevalue"
                           class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                           ${person.username}</a></b></p>
                    <div style="margin: 15px;">
                        <b>E-mail:</b> <a href="#" id="${person.id}_email" data-type="text" data-pk="${person.id}"
                                          data-title="Enter E-mail" data-url="${contextPath}/editresponsiblevalue"
                                          class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                            ${person.email}</a><br />

                        <b>Phone 1:</b> <a href="#" id="${person.id}_phone1" data-type="text" data-pk="${person.id}"
                                           data-title="Enter phone" data-url="${contextPath}/editresponsiblevalue"
                                           class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                            ${person.phone1}</a><br />

                        <b>Skype:</b> <a href="#" id="${person.id}_skype" data-type="text" data-pk="${person.id}"
                                         data-title="Enter skype" data-url="${contextPath}/editresponsiblevalue"
                                         class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                            ${person.skype}</a><br />

                        <b>Address:</b> <a href="#" id="${person.id}_address" data-type="text" data-pk="${person.id}"
                                           data-title="Enter address" data-url="${contextPath}/editresponsiblevalue"
                                           class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                            ${person.address}</a><br />

                        <b>Description:</b> <a href="#" id="${person.id}_description" data-type="text" data-pk="${person.id}"
                                               data-title="Enter description" data-url="${contextPath}/editresponsiblevalue"
                                               class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                            ${person.description}</a><br />

                        <b>Birthday:</b> <a href="#" id="${person.id}_birthday" data-type="date" data-pk="${person.id}"
                                            data-url="${contextPath}/editresponsiblevalue"
                                            class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                            ${person.birthday}</a><br />

                        <script>
                            $(document).ready(function() {
                                $('#${person.id}_username').editable();
                                $('#${person.id}_email').editable();
                                $('#${person.id}_phone1').editable();
                                $('#${person.id}_skype').editable();
                                $('#${person.id}_address').editable();
                                $('#${person.id}_description').editable();
                                $('#${person.id}_birthday').editable({
                                    placement: function (context, source) {
                                        var popupWidth = 336;
                                        if(($(window).scrollLeft() + popupWidth) > $(source).offset().left){
                                            return "right";
                                        } else {
                                            return "left";
                                        }
                                    }
                                });
                            });
                        </script>

                    </div>
                </div><br />
            </c:forEach>

        </div>
    </div>

</div>

<%@ include file = "footer.jsp"%>