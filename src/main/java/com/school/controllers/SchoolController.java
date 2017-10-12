package com.school.controllers;

import com.school.models.School;
import com.school.services.SchoolService;
import com.school.validator.SchoolValidator;
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
public class SchoolController {

    @Autowired
    SchoolValidator schoolValidator;

    @Autowired
    SchoolService schoolService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/schools", method = RequestMethod.GET)
    public String schools(Model model) {
        List<School> schools = schoolService.findAll();
        model.addAttribute("schools", schools);
        return "schools";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addschool", method = RequestMethod.GET)
    public String addschools(Model model){

        model.addAttribute("schoolForm", new School());

        return "addschool";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addschool", method = RequestMethod.POST)
    public String addschool(@ModelAttribute("schoolForm") School schoolForm, BindingResult bindingResult, Model model){
        schoolValidator.validate(schoolForm, bindingResult);

        if(bindingResult.hasErrors())
            return "addschool";

        model.addAttribute("message", "School " + schoolForm.getName() + " added successfully");
        schoolService.addSchool(schoolForm);

        return "addschool";
    }
}