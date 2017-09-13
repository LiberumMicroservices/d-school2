package com.school.utils;

import com.school.models.Course;
import com.school.models.User;

import java.util.List;

public interface CourseUtil {
    String editCourseOneValue(String name, String value, Long id);
    List<Course> freeCourses(User user);
    void deleteSchedule(Long courseId, Long scheduleId);
}
