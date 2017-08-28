package com.school.repositories;

import com.school.models.Course;
import com.school.models.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseByName(String name);
    List<Course> findBySchool(School school);
}
