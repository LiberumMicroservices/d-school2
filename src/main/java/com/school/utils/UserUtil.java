package com.school.utils;

import com.school.models.EditUser;
import com.school.models.School;
import com.school.models.User;
import com.school.models.UserSetting;

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
    User getCheckedUser(Long id);
    void saveCheckedUser(User user);
    void addUserToSchool(User user, String schoolName, String roleName);
    List<User> allUsersFromSchoolWithRole(School school, String role);
    String editUserOneValue(String name, String value, Long id);
    String editResponsiblePersonOneValue(String name, String value, Long id);
}
