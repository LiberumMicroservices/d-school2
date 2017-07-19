package com.school.controllers;

import com.school.models.EditUser;
import com.school.models.User;
import com.school.services.UserService;
import com.school.utils.UserUtils;
import com.school.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserUtils userUtils;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.GET)
    public String adduser(Model model){
        model.addAttribute("userForm", new User());

        return "adduser";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
        userValidator.validate(userForm, bindingResult);
        model.addAttribute("message", "User added successfully");

        if(bindingResult.hasErrors())
            return "adduser";

        userService.addUser(userForm);

        return "adduser";
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public String edituser(HttpServletRequest request, Model model){

        Long id = Long.parseLong(request.getParameter("id"));
        User user = userService.findById(id);

        model.addAttribute("userForm", userUtils.userToEditUser(user));

        model.addAttribute("roles", userUtils.allRoles());

        return "edituser";
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String edituser(@ModelAttribute("userForm") EditUser userForm, Model model){
        model.addAttribute("message", "Changes saved successfully");
        model.addAttribute("roles", userUtils.allRoles());

        userService.save(userUtils.editUserToUser(userForm));


        return "edituser";
    }
}
