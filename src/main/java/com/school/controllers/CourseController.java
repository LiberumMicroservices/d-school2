package com.school.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.models.Course;
import com.school.models.ResponseMsg;
import com.school.models.User;
import com.school.services.CourseService;
import com.school.services.RoomService;
import com.school.services.UserService;
import com.school.utils.CourseUtil;
import com.school.validator.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    CourseValidator courseValidator;

    @Autowired
    CourseUtil courseUtil;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/coursedetails", method = RequestMethod.GET)
    public String coursedetails(Model model, HttpServletRequest request){

        Course course;
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            course = courseService.findById(id);
        }catch (Exception e){
            return "redirect:/error?message=user not found";
        }

        model.addAttribute("course", course);
        model.addAttribute("rooms", roomService.findAllFromCurrentSchool());

        return "coursedetails";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/editcourse", method = RequestMethod.POST)
    public @ResponseBody
    String editcourse(@RequestParam() String value, @RequestParam() Long pk, @RequestParam() String name) throws JsonProcessingException {

        String message = courseUtil.editCourseOneValue(name, value, pk);
        ObjectMapper mapper = new ObjectMapper();
        ResponseMsg responseMsg = new ResponseMsg();

        if(message.equals("ok")) {
            responseMsg.setSuccess(true);
        } else {
            responseMsg.setSuccess(false);
            responseMsg.setMsg(message);
        }

        return mapper.writeValueAsString(responseMsg);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/courseuser", method = RequestMethod.GET)
    public String courseuser(HttpServletRequest request){

        Long userId;
        try {
            userId = Long.parseLong(request.getParameter("user"));
            User user = userService.findById(userId);
            Course course = courseService.findByName(request.getParameter("course"));
            String action = request.getParameter("action");
            switch (action){
                case "del":
                    userService.deleteCourse(user, course); break;
                case "add":
                    userService.addCourse(user, course); break;
                default:
                    return "redirect:/error?message=Error operation " + action;
            }

        }catch (Exception e){
            return "redirect:/error?message=Not found";
        }

        String referer = request.getHeader("referer");

        return "redirect:" + referer;

    }




}
