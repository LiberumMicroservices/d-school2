package com.school.services;

import com.school.models.Course;
import com.school.models.School;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    void save(Course course);
    void deleteById(Long id);
    void block(Course course);
    void unblock(Course course);
    Course findById(Long id);
    Course findByName(String name);
    List<Course> findAll();
    List<Course> findAllFromSchool(School school);
    List<Course> findAllFromCurrentSchool();
}
