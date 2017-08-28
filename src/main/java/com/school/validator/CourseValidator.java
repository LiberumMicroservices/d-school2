package com.school.validator;

import com.school.models.Course;
import com.school.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CourseValidator implements Validator {

    @Autowired
    CourseService courseService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Course.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Course course = (Course) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if(course.getName().length() < 4 || course.getName().length() > 32)
            errors.rejectValue("name", "Size.courseForm.name");
        if(courseService.findByName(course.getName()) != null)
            errors.rejectValue("name", "Duplicate.name");

        if(course.getStartDate() != null && course.getEndDate() != null)
            if(course.getStartDate().isAfter(course.getEndDate()))
                errors.rejectValue("endDate", "Before.courseForm.date");
    }
}
