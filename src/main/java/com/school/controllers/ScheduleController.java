package com.school.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.models.Course;
import com.school.models.Schedule;
import com.school.models.ScheduleJson;
import com.school.services.CourseService;
import com.school.services.RoomService;
import com.school.services.ScheduleService;
import com.school.services.SchoolService;
import com.school.utils.CourseUtil;
import com.school.utils.ScheduleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CourseService courseService;

    @Autowired
    RoomService roomService;

    @Autowired
    CourseUtil courseUtil;

    @Autowired
    SchoolService schoolService;

    @Autowired
    ScheduleUtils scheduleUtils;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/addschedule", method = RequestMethod.GET)
    public String addschedule(HttpServletRequest request){

        try {
            Long courseId = Long.parseLong(request.getParameter("course"));
            String roomName = request.getParameter("room");
            String day = request.getParameter("day");
            String time = request.getParameter("time");

            Schedule schedule = new Schedule();
            schedule.setCourse(courseService.findById(courseId));
            schedule.setRoom(roomService.findByName(roomName));
            schedule.setDay(DayOfWeek.valueOf(day.toUpperCase()));
            schedule.setTime(LocalTime.parse(time));
            scheduleService.addSchedule(schedule);

        }catch (Exception e){
            return "redirect:/error?message= " + e.getMessage();
        }

        String referer = request.getHeader("referer");

        return "redirect:" + referer;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/delschedule", method = RequestMethod.GET)
    public String delschedule(HttpServletRequest request){

        try {
            Long scheduleId = Long.parseLong(request.getParameter("scheduleId"));
            scheduleService.remove(scheduleId);
        }catch (Exception e){
            return "redirect:/error?message= " + e.getMessage();
        }

        String referer = request.getHeader("referer");

        return "redirect:" + referer;
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER', 'ROLE_TEACHER')")
    @RequestMapping(value = "/schedules", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody
    String schedules(HttpServletResponse httpServletResponse) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            List<ScheduleJson> schedules = new ArrayList<>();

            List<Course> courses = courseService.findAllFromSchool(schoolService.findByName("test"));

            for (Course c: courses)
                for (Schedule s: c.getSchedules())
                    schedules.add(scheduleUtils.toJsonObject(s));

            return mapper.writeValueAsString(schedules);

        }catch (Exception e){

            return mapper.writeValueAsString("Error");
        }
    }

    //TODO schedules list to student and teacher
}
