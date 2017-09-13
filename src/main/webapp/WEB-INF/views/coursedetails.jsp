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
 <%--Add timepicker--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/js/bootstrap-timepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/css/bootstrap-timepicker.min.css" />

<div class="container-fluid">

    <div class="panel panel-info">
        <div class="panel-heading"><p class="text-info">
            <span>${message}</span>
            <a href="#" id="name" data-type="text" data-pk="${course.id}" data-title="Enter course name" data-url="${contextPath}/editcourse"
               class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                <b>${course.name}</b></a></p>
        </div>
        <div class="panel-body">
            <b>Start:</b> <a href="#" id="startDate" data-type="date" data-pk="${course.id}"
                                data-url="${contextPath}/editcourse" class="editable editable-click"
                                data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
            ${course.startDate}</a><br />

            <b>End:</b> <a href="#" id="endDate" data-type="date" data-pk="${course.id}"
                             data-url="${contextPath}/editcourse" class="editable editable-click"
                             data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
            ${course.endDate}</a><br />

            <b>Description:</b> <a href="#" id="description" data-type="text" data-pk="${course.id}" data-title="Enter description"
                                   data-url="${contextPath}/editcourse" class="editable editable-click"
                                   data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
            ${course.description}</a><br />


            <script>
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
        <div class="panel-heading">
            <p class="text-info">Schedules</p>
        </div>
        <div class="panel-body">
            <table class="table table-hover table-fit">
            <c:forEach var="schedule" items="${course.schedules}">
                <tr>
                    <td>${schedule.day}</td>
                    <td>${schedule.time}</td>
                    <td>${schedule.room.address}&emsp;</td>
                    <td>
                        <a href="${contextPath}/delschedule?courseId=${course.id}&scheduleId=${schedule.id}">
                        <span class="glyphicon glyphicon-remove" style="color:red"></span></a>
                    </td>
                </tr>
            </c:forEach>
            </table>

            <div class="input-group">
                <select class="form-control" id="room" style="width:auto;">
                    <c:forEach var="room" items="${rooms}">
                        <option>${room.name}</option>
                    </c:forEach>
                </select>

                <select class="form-control" id="days" style="width:auto;">
                    <option>Monday</option>
                    <option>Tuesday</option>
                    <option>Wednesday</option>
                    <option>Thursday</option>
                    <option>Friday</option>
                    <option>Saturday</option>
                    <option>Sunday</option>
                </select>

                <input id="timepicker" type="text" class="form-control input-small" style="width:auto;">

                <a href="#" type="button" class="btn btn-default" onclick="addSchedule()">Add schedule</a>
            </div>

            <script type="text/javascript">
                $('#timepicker').timepicker({
                    showMeridian: false,
                    minuteStep: 5
                });

                function addSchedule(){
                    var room = $('#room').val();
                    var day = $('#days').val();
                    var time = $('#timepicker').val();
                    location.href = "${contextPath}/addschedule?course=${course.id}&room=" + room + "&day=" + day + "&time=" + time;
                }
            </script>

        </div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">
            <p class="text-info">Students</p>
        </div>
        <div class="panel-body">
            <table class="table table-striped" id="usersTable">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>E-mail</th>
                    <th>Phone</th>
                    <th>Description</th>
                    <th>Birthday</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${course.users}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.phone1}</td>
                        <td>${user.description}</td>
                        <td>${user.birthday}</td>
                        <td><a href="${contextPath}/studentdetails?id=${user.id}">Details</a>&emsp;
                            <a href="${contextPath}/courseuser?user=${user.id}&course=${course.name}&action=del">
                            <span class="glyphicon glyphicon-remove" style="color:red"></span></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

    <div class="panel-body">
<%@ include file = "footer.jsp"%>