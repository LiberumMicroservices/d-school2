package com.school.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.models.*;
import com.school.repositories.RoleRepository;
import com.school.services.ResponsiblePersonService;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

//@SessionAttributes(types = User.class)
//@SessionAttributes(value = "currentSchoolName")
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
    RoleRepository roleRepository;

    @Autowired
    SchoolService schoolService;

    @Autowired
    ResponsiblePersonService responsiblePersonService;

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
            System.out.println("UserController.users: " + e);
        }

        List<User> users = userService.allUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    public String deleteuser(HttpServletRequest request, Model model) {
        model.addAttribute("currentSchool", currentSchoolName());

        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        if(userService.findCurrentUser().getRoles().contains(roleUser)) {
            return "redirect:/login";
        }
        else {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                userService.deleteById(id);
                return "redirect:/users";
            } catch (Exception e) {
                System.out.println("UserController.deleteuser: " + e);
            }
        }

        return "redirect:/users";
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

        // --- EDIT MAIN USER INFO ---
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

        return "edituser";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String edituser(@ModelAttribute("userForm") EditUser userForm, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        model.addAttribute("message", "Changes saved successfully");
        model.addAttribute("rolesadm", userUtil.rolesForAdmin());
        userService.save(userUtil.editUserToUser(userForm));

        return "edituser";
    }

    @RequestMapping(value = "/userdetails", method = RequestMethod.GET)
    public String userdetails(HttpServletRequest request, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            // Use access check
            User user = userUtil.getCheckedUser(id);
            model.addAttribute("userForm", user);
            model.addAttribute("user", user);
        }catch (Exception e){
            model.addAttribute("message", "User not found");
        }
        return "userdetails";
    }


    @RequestMapping(value = "/userdetails", method = RequestMethod.POST)
    public String userdetails(@ModelAttribute("userForm") User userForm, HttpServletRequest request, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            User user = userService.findById(id);

            // Use access check
            userUtil.saveCheckedUser(userForm);
            model.addAttribute("message", "Changes saved successfully");

            return "redirect:/userdetails?id=" + user.getId();
        }catch (Exception e){
            model.addAttribute("message", "Changes saved error");
        }

        return "userdetails";
    }

    @Secured({"ROLE_ADMIN, ROLE USER"})
    @RequestMapping(value = "/usersetting", method = RequestMethod.POST)
    public String usersetting(@ModelAttribute("userSetting") UserSetting userSetting, Model model){
        model.addAttribute("currentSchool", currentSchoolName());

        User user = userService.findCurrentUser();
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

        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("userSetting", userUtil.userToUserSetting(user));
        model.addAttribute("schoolList", userUtil.schoolsForUser(user));

        return "usersetting";
    }

    //TODO сделать сохранение школы в сессии
//    @ModelAttribute("currentSchoolName")
//    public String studentEmail() {
//        return "";
//    }


    //   --- RESPONSIBLE PERSON ---
    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/addresponsibleperson", method = RequestMethod.GET)
    public String addresponsibleperson(Model model, HttpServletRequest request){
        model.addAttribute("currentSchool", currentSchoolName());
        model.addAttribute("responsibleForm", new ResponsiblePerson());

        User student;
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            student = userService.findById(id);
        }catch (Exception e){
            return "redirect:/error?message=user not found";
        }

        model.addAttribute("student", student);

        return "addresponsibleperson";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/addresponsibleperson", method = RequestMethod.POST)
    public String addresponsibleperson(@ModelAttribute("responsibleForm") ResponsiblePerson responsiblePerson,
            Model model, HttpServletRequest request){

        model.addAttribute("currentSchool", currentSchoolName());

        User student;

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            student = userService.findById(id);
            responsiblePerson.setUser(student);
            responsiblePersonService.save(responsiblePerson);
        }catch (Exception e){
            return "redirect:/error?message=user not found";
        }

        return "redirect:/studentdetails?id=" + student.getId();
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
    @RequestMapping(value = "/studentdetails", method = RequestMethod.GET)
    public String studentdetails(ModelMap model, HttpServletRequest request){

        User user;
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            user = userService.findById(id);
        }catch (Exception e){
            return "redirect:/error?message=user not found";
        }

        model.addAttribute("user", user);

        return "studentdetails";
    }

    @RequestMapping(value = "/editstudentvalue", method = RequestMethod.POST)
    public @ResponseBody String editstudentvalue(@RequestParam() String value, @RequestParam() Long pk, @RequestParam() String name) throws JsonProcessingException {

        String message = userUtil.editUserOneValue(name, value, pk);
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

    @RequestMapping(value = "/editresponsiblevalue", method = RequestMethod.POST)
    public @ResponseBody String editresponsiblevalue(@RequestParam() String value, @RequestParam() Long pk, @RequestParam() String name) throws JsonProcessingException {
        String message = userUtil.editResponsiblePersonOneValue(name, value, pk);
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


    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/addstudent", method = RequestMethod.GET)
    public String addStudent(Model model){
        model.addAttribute("currentSchool", currentSchoolName());
        model.addAttribute("studentForm", new User());

        return "addstudent";
    }

    @Secured({"ROLE_ADMIN, ROLE_BOSS, ROLE_MANAGER, ROLE_TEACHER"})
    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("studentForm") User userForm, Model model, BindingResult bindingResult){
        model.addAttribute("currentSchool", currentSchoolName());

        userValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors())
            return "addstudent";

        // save student
        userUtil.createStudent(userForm, currentSchoolName());

        return "redirect:/addresponsibleperson?id="+userService.findByEmail(userForm.getEmail());
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

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model, HttpServletRequest request){
        model.addAttribute("currentSchool", currentSchoolName());
        String message = request.getParameter("message");
        model.addAttribute("message", message);

        return "error";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model){

        List<ResponsiblePerson> responsiblePersons = new ArrayList<>();
        ResponsiblePerson r1 = new ResponsiblePerson();
        ResponsiblePerson r2 = new ResponsiblePerson();
        User user = userService.findCurrentUser();
        r1.setUser(user);
        r2.setUser(user);
        r1.setUsername("name1");
        r2.setUsername("name2");
        r1.setEmail("mail1");
        r2.setEmail("mail2");
        r1.setSkype("skype1");
        r2.setSkype("skype2");
        r1.setPhone1("+380679547354");
        r2.setPhone1("+380976582165");
        r1.setAddress("address1");
        r2.setAddress("address2");
        r1.setDescription("description1");
        r2.setDescription("description2");
        responsiblePersons.add(r1);
        responsiblePersons.add(r2);

        AddStudentForm asf = new AddStudentForm();
        asf.setUser(user);
        asf.setResponsiblePersons(responsiblePersons);

        model.addAttribute("userForm", asf);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("addStudentForm", asf);

        return "test";
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

}
