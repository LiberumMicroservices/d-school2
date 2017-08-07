package com.school.services.impl;

import com.school.models.Role;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.repositories.UserRepository;
import com.school.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findOne(1L));
        user.setRoles(roles);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        Long id = user.getId();
        user.setPassword(findById(id).getPassword());
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findByPhone1(String phone1) {
        return userRepository.findUserByPhone1(phone1);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }



}
