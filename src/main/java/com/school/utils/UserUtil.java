package com.school.utils;

import com.school.models.*;

import java.util.List;

public interface UserUtil {

    EditUser userToEditUser(User user);
    User editUserToUser(EditUser editUser);
    UserSetting userToUserSetting(User user);
    User saveSettingToUser(UserSetting userSetting);
    List<String> allRoles();
    List<String> rolesForAdmin();
    List<String> rolesForSchool();
    List<String> schoolsForUser(User user);
    User addBoss(User user, School school);
    User addManager(User user, School school);
    User createManager(User user, String schoolName);
    User createTeacher(User user, String schoolName);
    User createStudent(User user, String schoolName);
    User addTeacher(User user, School school);
    User addStudent(User user, School school);
    void addUserToSchool(User user, String schoolName, String roleName);
    List<User> allUsersFromSchoolWithRole(School school, String role);
}
