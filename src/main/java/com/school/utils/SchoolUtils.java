package com.school.utils;

import com.school.models.User;

import java.util.List;

public interface SchoolUtils {
    List<String> schoolList();
    List<String> freeSchoolsForUserList(User user);
    List<String> schoolsForUserList(User user);
    String currentSchoolName();
}
