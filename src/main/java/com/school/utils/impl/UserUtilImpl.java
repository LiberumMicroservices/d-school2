package com.school.utils.impl;

import com.school.models.*;
import com.school.repositories.RoleRepository;
import com.school.services.SchoolAccountService;
import com.school.services.SchoolService;
import com.school.services.SecurityService;
import com.school.services.UserService;
import com.school.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserUtilImpl implements UserUtil {

    @Autowired
    private UserService userService;

    @Autowired
    SchoolService schoolService;

    @Autowired
    private SchoolAccountService schoolAccountService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    SecurityService securityService;

    @Override
    public EditUser userToEditUser(User user){
        EditUser editUser = new EditUser();

        editUser.setId(user.getId());
        editUser.setUsername(user.getUsername());
        editUser.setEmail(user.getEmail());

        if(user.getRoles().size() > 0)
            editUser.setRole(user.getRoles().iterator().next().getName());

        return editUser;
    }

    @Override
    public User editUserToUser(EditUser editUser){
        User user = userService.findById(editUser.getId());

        user.setUsername(editUser.getUsername());
        user.setEmail(editUser.getEmail());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(editUser.getRole()));

        user.setRoles(roles);

        return user;
    }

    @Override
    public UserSetting userToUserSetting(User user) {
        UserSetting res = new UserSetting();
        res.setId(user.getId());
        res.setCurrentSchool(user.getCurrentSchoolName());
        res.setSchoolList(schoolsForUser(user));

        return res;
    }

    @Override
    public User saveSettingToUser(UserSetting userSetting) {
        User res = userService.findById(userSetting.getId());
        res.setCurrentSchoolName(userSetting.getCurrentSchool());
        userService.save(res);

        return res;
    }

    public List<String> allRoles(){
        List<String> roles = new ArrayList<>();

        List<Role> rolesSet = new ArrayList<>(roleRepository.findAll());

        for(Role r: rolesSet)
            roles.add(r.getName());

        return roles;
    }

    @Secured("ROLE_ADMIN")
    @Override
    public List<String> rolesForAdmin() {
        List<String> res = new ArrayList<>();

        res.add("ROLE_ADMIN");
        res.add("ROLE_USER");

        return res;
    }

    @Override
    public List<String> rolesForSchool() {
        List<String> res = new ArrayList<>();

        res.add("ROLE_STUDENT");
        res.add("ROLE_TEACHER");
        res.add("ROLE_MANAGER");
        res.add("ROLE_BOSS");

        return res;
    }

    @Override
    public List<String> schoolsForUser(User user) {
        List<String> res = new ArrayList<>();
        Set<SchoolAccount> schoolAccounts = user.getSchoolAccounts();
        if(schoolAccounts == null)
            res.add("none");
        else
            res.addAll(schoolAccounts.stream().map(sa -> sa.getSchool().getName()).collect(Collectors.toList()));

        return res;
    }

    @Secured("ROLE_ADMIN")
    @Override
    public User addBoss(User user, School school) {
        Role role = roleRepository.findRoleByName("ROLE_BOSS");
        SchoolAccount schoolAccount = schoolAccountService.findByUserAndShool(user, school);
        if(schoolAccount == null) {
            schoolAccount = createSchoolAccount(user, school);
        }

        schoolAccount.setRole(role);

        userService.save(user);

        return user;
    }

    @Secured({"ROLE_ADMIN", "ROLE_BOSS"})
    @Override
    public User addManager(User user, School school) {
        Role role = roleRepository.findRoleByName("ROLE_MANAGER");
        SchoolAccount schoolAccount = schoolAccountService.findByUserAndShool(user, school);
        if(schoolAccount == null)
            schoolAccount = createSchoolAccount(user, school);

        schoolAccount.setRole(role);
        userService.save(user);

        return user;
    }

    @Secured({"ROLE_ADMIN", "ROLE_BOSS"})
    @Override
    public User createManager(User user, String schoolName) {
        userService.addUser(user);
        addUserToSchool(userService.findByEmail(user.getEmail()), schoolName, "ROLE_MANAGER");
        return user;
    }

    @Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_MANAGER"})
    @Override
    public User addTeacher(User user, School school) {
        Role role = roleRepository.findRoleByName("ROLE_TEACHER");
        SchoolAccount schoolAccount = schoolAccountService.findByUserAndShool(user, school);
        if(schoolAccount == null)
            schoolAccount = createSchoolAccount(user, school);

        schoolAccount.setRole(role);
        userService.save(user);

        return user;
    }

    @Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_MANAGER"})
    @Override
    public User createTeacher(User user, String schoolName) {
        userService.addUser(user);
        addUserToSchool(userService.findByEmail(user.getEmail()), schoolName, "ROLE_TEACHER");
        return user;
    }

    @Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_MANAGER", "ROLE_TEACHER"})
    @Override
    public User addStudent(User user, School school) {
        Role role = roleRepository.findRoleByName("ROLE_STUDENT");
        SchoolAccount schoolAccount = schoolAccountService.findByUserAndShool(user, school);
        if(schoolAccount == null)
            schoolAccount = createSchoolAccount(user, school);

        schoolAccount.setRole(role);
        userService.save(user);

        return user;
    }

    @Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_MANAGER", "ROLE_TEACHER"})
    @Override
    public User createStudent(User user, String schoolName) {
        userService.addUser(user);
        addUserToSchool(userService.findByEmail(user.getEmail()), schoolName, "ROLE_STUDENT");
        return user;
    }

    @Override
    public void addUserToSchool(User user, String schoolName, String roleName) {
        Role role = roleRepository.findRoleByName(roleName);
        School school = schoolService.findByName(schoolName);
        if(schoolAccountService.findByUserAndShool(user, school) == null) {
            SchoolAccount schoolAccount = createSchoolAccount(user, school);
            schoolAccount.setRole(role);
            schoolAccountService.save(schoolAccount);
        }
    }

    @Override
    public List<User> allUsersFromSchoolWithRole(School school, String role) {
        List<User> users = new ArrayList<>();
        List<SchoolAccount> schoolAccounts = schoolAccountService.findBySchool(school);
        for (SchoolAccount schoolAccount: schoolAccounts)
            if(schoolAccount.getRole().getName().equals(role))
                users.add(schoolAccount.getUser());

        return users;
    }

    private SchoolAccount createSchoolAccount(User user, School school){
        SchoolAccount schoolAccount = new SchoolAccount();
        schoolAccount.setEnabled(true);
        schoolAccount.setDateOfEnrolment(LocalDate.now());
        schoolAccount.setSchool(school);
        schoolAccount.setUser(user);

        if(user.getCurrentSchoolName() == null)
            user.setCurrentSchoolName(schoolAccount.getSchool().getName());

        return schoolAccount;
    }


}
