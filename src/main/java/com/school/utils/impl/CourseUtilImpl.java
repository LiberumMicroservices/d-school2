package com.school.utils.impl;

import com.school.models.Course;
import com.school.models.Schedule;
import com.school.models.User;
import com.school.services.CourseService;
import com.school.services.ScheduleService;
import com.school.services.UserService;
import com.school.utils.CourseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CourseUtilImpl implements CourseUtil {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Override
    public String editCourseOneValue(String name, String value, Long id) {
        try {
            Course course = courseService.findById(id);

            switch (name){
                case "name":
                    if(value.length() < 6 || value.length() > 32)
                        return "between 6 and 32 characters";
                    course.setName(value);
                    break;

                case "startDate":
                    try {
                        course.setStartDate(LocalDate.parse(value));
                    }catch (Exception e){
                        return "Error date format";
                    } break;

                case "endDate":
                    try {
                        if(course.getStartDate() != null && LocalDate.parse(value) != null)
                            if(course.getStartDate().isAfter(LocalDate.parse(value)))
                                return "End date is earlier than start date";
                        course.setEndDate(LocalDate.parse(value));
                    }catch (Exception e){
                        return "Error date format";
                    } break;

                case "description": course.setDescription(value); break;

                default: return name + " is not found";
            }

            courseService.save(course);
            return "ok";
        }catch (Exception e){
            return "error update";
        }
    }

    @Override
    public List<Course> freeCourses(User user) {
        List<Course> res = new ArrayList<>();
        List<Course> all = courseService.findAll();
        for (Course course: all)
            if(!isUserCourse(course, user))
                res.add(course);

        return res;
    }

    @Override
    public void deleteSchedule(Long courseId, Long scheduleId) {
        Course course = courseService.findById(courseId);
        if(course.getSchedules() != null) {
            Set<Schedule> schedules = course.getSchedules();
            Set<Schedule> schedulesRes = new HashSet<>();
            for(Schedule s: schedules)
                if(s.getId() != scheduleId)
                    schedulesRes.add(s);

            course.setSchedules(schedulesRes);
            courseService.save(course);
        }

    }

    boolean isUserCourse(Course course, User user){
        if(user.getCourses() != null)
            for (Course c: user.getCourses())
                if(c.getId() == course.getId())
                    return true;

        return false;
    }
}
