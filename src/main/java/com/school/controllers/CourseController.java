package com.school.controllers;

import com.school.models.Course;
import com.school.services.CourseService;
import com.school.validator.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    CourseValidator courseValidator;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String courses(Model model) {
        List<Course> courses = courseService.findAllFromCurrentSchool();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/addcourse", method = RequestMethod.GET)
    public String addcourse(Model model){

        model.addAttribute("courseForm", new Course());

        return "addcourse";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/addcourse", method = RequestMethod.POST)
    public String addcourse(@ModelAttribute("courseForm") Course courseForm, BindingResult bindingResult){
        courseValidator.validate(courseForm, bindingResult);

        if(bindingResult.hasErrors())
            return "addcourse";

        courseService.addCourse(courseForm);

        return "redirect:/courses";
    }

}
