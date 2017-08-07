package com.school.controllers;

import com.school.models.*;
import com.school.services.SchoolAccountService;
import com.school.services.SchoolService;
import com.school.services.UserService;
import com.school.utils.SchoolUtils;
import com.school.utils.UserUtil;
import com.school.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserUtil userUtil;

    @Autowired
    private SchoolUtils schoolUtils;

    @Autowired
    private SchoolAccountService schoolAccountService;

    @Autowired
    SchoolService schoolService;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {

        model.addAttribute("currentSchool", currentSchoolName());

        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        model.addAttribute("currentSchool", currentSchoolName());

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(HttpServletRequest request, Model model) {
        model.addAttribute("currentSchool", currentSchoolName());

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));

            User user = userService.findById(id);
            user.setEnabled(enabled);
            userService.save(user);
            String res = enabled ? "Unblocked" : "Blocked";
            model.addAttribute("message", "User " + user.getUsername() + " " + res);

        }catch (Exception e){

        }

        List<User> users = userService.allUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adduser", method = RequestMethod.GET)
    public String adduser(Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        model.addAttribute("userForm", new User());

        return "adduser";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
        model.addAttribute("currentSchool", currentSchoolName());
        model.addAttribute("roles", userUtil.allRoles());

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors())
            return "adduser";

        model.addAttribute("message", "User added successfully");

        userService.addUser(userForm);

        return "adduser";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public String edituser(HttpServletRequest request, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        // Edit main user info
        Long id = Long.parseLong(request.getParameter("id"));
        User user = userService.findById(id);
        model.addAttribute("userForm", userUtil.userToEditUser(user));
        model.addAttribute("rolesadm", userUtil.rolesForAdmin());

        if(request.getParameter("action") != null) {
            if (request.getParameter("action").equals("addSchool"))
                userUtil.addUserToSchool(user, request.getParameter("school"), request.getParameter("role"));

            if (request.getParameter("action").equals("ban")) {
                Long schoolAccountId = Long.parseLong(request.getParameter("schoolAccountId"));
                SchoolAccount sa = schoolAccountService.findById(schoolAccountId);
                boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));
                sa.setEnabled(enabled);
                schoolAccountService.save(sa);
            }
        }
        user = userService.findById(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("roles", userUtil.rolesForSchool());
        model.addAttribute("schools", schoolUtils.freeSchoolsForUserList(user));
        String schoolName = "";
        String roleName = "";
        model.addAttribute("schoolName", schoolName);
        model.addAttribute("roleName", roleName);

//        id=2&action=addSchool&school=salsa&role=ROLE_STUDENT

        return "edituser";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String edituser(@ModelAttribute("userForm") EditUser userForm, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        model.addAttribute("message", "Changes saved successfully");
        model.addAttribute("rolesadm", userUtil.rolesForAdmin());
        userService.save(userUtil.editUserToUser(userForm));

//        model.addAttribute("roles", userUtil.rolesForSchool());

        return "edituser";
    }

    @Secured({"ROLE_ADMIN, ROLE USER"})
    @RequestMapping(value = "/usersetting", method = RequestMethod.POST)
    public String usersetting(@ModelAttribute("userSetting") UserSetting userSetting, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        User user = currentUser();
        model.addAttribute("user", user);
        model.addAttribute("userSetting", userUtil.userToUserSetting(user));
        model.addAttribute("schoolList", userUtil.schoolsForUser(user));

        try {
            userSetting.setId(user.getId());
            userUtil.saveSettingToUser(userSetting);
            return "redirect:/logout";
        }catch (Exception e){
            model.addAttribute("message", "Error change current school");
        }

        return "usersetting";
    }

    @Secured({"ROLE_ADMIN, ROLE_USER"})
    @RequestMapping(value = "/usersetting", method = RequestMethod.GET)
    public String usersetting(Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        User user = currentUser();
        model.addAttribute("user", user);
        model.addAttribute("userSetting", userUtil.userToUserSetting(user));
        model.addAttribute("schoolList", userUtil.schoolsForUser(user));

        return "usersetting";
    }

    // --- STUDENTS ---

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String students(Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        School school = schoolService.findByName(currentSchoolName());
        model.addAttribute("students", userUtil.allUsersFromSchoolWithRole(school, "ROLE_STUDENT"));

        return "students";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/addstudent", method = RequestMethod.GET)
    public String addStudent(Model model){
        model.addAttribute("currentSchool", currentSchoolName());
        model.addAttribute("userForm", new User());

        return "addstudent";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("userForm") User userForm, Model model, BindingResult bindingResult){
        model.addAttribute("currentSchool", currentSchoolName());

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors())
            return "addstudent";

        userUtil.createStudent(userForm, currentSchoolName());

        model.addAttribute("message", "Student " + userForm.getUsername() + " added successfully");

        return "addstudent";
    }

    // --- TEACHERS ---

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER"})
    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public String teachers(Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        School school = schoolService.findByName(currentSchoolName());
        model.addAttribute("teachers", userUtil.allUsersFromSchoolWithRole(school, "ROLE_TEACHER"));

        return "teachers";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER"})
    @RequestMapping(value = "/addteacher", method = RequestMethod.GET)
    public String addTeacher(Model model){
        model.addAttribute("currentSchool", currentSchoolName());
        model.addAttribute("userForm", new User());

        return "addteacher";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER"})
    @RequestMapping(value = "/addteacher", method = RequestMethod.POST)
    public String addTeacher(@ModelAttribute("userForm") User userForm, Model model, BindingResult bindingResult){
        model.addAttribute("currentSchool", currentSchoolName());

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors())
            return "addteacher";

        userUtil.createTeacher(userForm, currentSchoolName());

        model.addAttribute("message", "Teacher " + userForm.getUsername() + " added successfully");

        return "addteacher";
    }

    // --- MANAGERS ---

    @Secured({"ROLE_ADMIN, ROLE_BOSS"})
    @RequestMapping(value = "/managers", method = RequestMethod.GET)
    public String managers(Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        School school = schoolService.findByName(currentSchoolName());
        model.addAttribute("managers", userUtil.allUsersFromSchoolWithRole(school, "ROLE_MANAGER"));

        return "managers";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS"})
    @RequestMapping(value = "/addmanager", method = RequestMethod.GET)
    public String addmanager(Model model){
        model.addAttribute("currentSchool", currentSchoolName());
        model.addAttribute("userForm", new User());

        return "addmanager";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS"})
    @RequestMapping(value = "/addmanager", method = RequestMethod.POST)
    public String addmanager(@ModelAttribute("userForm") User userForm, Model model, BindingResult bindingResult){
        model.addAttribute("currentSchool", currentSchoolName());

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors())
            return "addmanager";

        userUtil.createManager(userForm, currentSchoolName());

        model.addAttribute("message", "Manager " + userForm.getUsername() + " added successfully");

        return "addmanager";
    }

    private User currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User res;
        try {
            res = userService.findByUsername(name);
        } catch (Exception e){
            res = new User();
        }

        return res;
    }

    private String currentSchoolName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        String schoolName;
        if(!name.equals("anonymousUser")) {
            schoolName = userService.findByUsername(name).getCurrentSchoolName();
            if(schoolName == null)
                schoolName = "D-School";
        }
        else {
            schoolName = "D-School";
        }

        return schoolName;
    }

//    @RequestMapping(value = "/schoolAccounts.json", method = RequestMethod.GET)
//    public @ResponseBody
//    String getTime() throws JsonProcessingException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        User user = userService.findById(1L);
//        String res = mapper.writeValueAsString(user.getSchoolAccounts());
//
//        return res;
//    }

    //TODO
//
//    public String addteacher(){
//
//        return "addmanager";
//    }
//
//    public String addstudent(){
//
//        return "addmanager";
//    }
}
