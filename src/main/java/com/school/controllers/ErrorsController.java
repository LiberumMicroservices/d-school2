package com.school.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController {

    @RequestMapping(value = "/errors/405.html")
    public ModelAndView handle405(Model model) {

        ModelAndView modelAndView = new ModelAndView("viewName");
        modelAndView.addObject("errorCode", "405");
        modelAndView.addObject("message", "Error 405 happens");

        return modelAndView;
    }
}
