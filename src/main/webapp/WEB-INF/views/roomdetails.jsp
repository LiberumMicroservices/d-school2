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
 <%--Add datatable--%>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">

<div class="container-fluid">

    <div class="panel panel-info">
        <div class="panel-heading"><p class="text-info">
            <span>${message}</span>
            <a href="#" id="name" data-type="text" data-pk="${room.id}" data-title="Enter course name" data-url="${contextPath}/editcourse"
               class="editable editable-click" data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
                <b>${room.name}</b></a></p>
        </div>
        <div class="panel-body">
            <b>Address:</b> <a href="#" id="address" data-type="text" data-pk="${room.id}" data-title="Enter description"
                                   data-url="${contextPath}/editroom" class="editable editable-click"
                                   data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
            ${room.address}</a><br />

            <b>Phone:</b> <a href="#" id="phone1" data-type="text" data-pk="${room.id}" data-title="Enter description"
                                   data-url="${contextPath}/editroom" class="editable editable-click"
                                   data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
            ${room.phone1}</a><br />

            <b>Description:</b> <a href="#" id="description" data-type="text" data-pk="${room.id}" data-title="Enter description"
                                   data-url="${contextPath}/editroom" class="editable editable-click"
                                   data-params="{${_csrf.parameterName}:'${_csrf.token}'}">
            ${room.description}</a><br />


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
            <p>
                Show:
                <select id="table-filter">
                    <option value="">All</option>
                    <option value="MONDAY">Monday</option>
                    <option value="TUESDAY">Tuesday</option>
                    <option value="WEDNESDAY">Wednesday</option>
                    <option value="THURSDAY">Thursday</option>
                    <option value="FRIDAY">Friday</option>
                    <option value="SATURDAY">Saturday</option>
                    <option value="SUNDAY">Sunday</option>
                </select>
            </p>
            <table class="table table-hover table-fit" id="schedulesTable">
                <thead>
                <th></th>
                <th></th>
                <th></th>
                </thead>
                <tbody>
                    <c:forEach var="schedule" items="${room.schedules}">
                        <tr>
                            <td>${schedule.day}</td>
                            <td>${schedule.time}</td>
                            <td>${schedule.course.name}&emsp;</td>
                        </tr>
                    </c:forEach>
                    </tbody>
            </table>
            <script>
                $(document).ready(function (){
                    var table = $('#schedulesTable').DataTable({
                        dom: 'lrtip'
                    });

                    $('#table-filter').on('change', function(){
                        table.search(this.value).draw();
                    });
                });
//                $(function(){
//                    $("#schedulesTable").dataTable();
//                })
            </script>
        </div>
    </div>

</div>

<%@ include file = "footer.jsp"%>