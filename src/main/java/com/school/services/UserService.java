package com.school.services;

import com.school.models.Course;
import com.school.models.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findCurrentUser();
    void addUser(User user);
    void save(User user);
    void deleteById(Long id);
    void deleteCourse(User user, Course course);
    void addCourse(User user, Course course);
    void changePassword(Long id, String password);
    User findByUsername(String username);
    User findByPhone1(String phone1);
    User findByEmail(String email);
    List<User> allUsers();
}
