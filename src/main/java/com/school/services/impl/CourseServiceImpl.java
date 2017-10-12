package com.school.services.impl;

import com.school.models.Course;
import com.school.models.School;
import com.school.repositories.CourseRepository;
import com.school.services.CourseService;
import com.school.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SchoolService schoolService;

    @Override
    public void addCourse(Course course) {
        course.setSchool(schoolService.currentSchool());
        course.setEnabled(true);
        courseRepository.save(course);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.delete(id);
    }

    @Override
    public void block(Course course) {
        course.setEnabled(false);
        save(course);
    }

    @Override
    public void unblock(Course course) {
        course.setEnabled(true);
        save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findOne(id);
    }

    @Override
    public Course findByName(String name) {
        return courseRepository.findCourseByName(name);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllFromSchool(School school) {
        return courseRepository.findBySchool(school);
    }

    @Override
    public List<Course> findAllFromCurrentSchool() {
        return findAllFromSchool(schoolService.currentSchool());
    }
}
