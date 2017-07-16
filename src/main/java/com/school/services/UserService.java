package com.school.services;

import com.school.models.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    void addUser(User user);
    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> allUsers();
}
