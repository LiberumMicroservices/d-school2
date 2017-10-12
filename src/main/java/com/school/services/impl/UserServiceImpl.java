package com.school.services.impl;

import com.school.models.Course;
import com.school.models.Role;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.repositories.UserRepository;
import com.school.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public User findCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User res;
        try {
            res = findByUsername(name);
        } catch (Exception e){
            res = new User();
        }

        return res;
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
        User tmp = findById(user.getId());
        user.setPassword(tmp.getPassword());
        user.setEnabled(tmp.isEnabled());

        if(user.getUsername() == null && tmp.getUsername() != null)
            user.setUsername(tmp.getUsername());

        if(user.getEmail() == null && tmp.getEmail() != null)
            user.setEmail(tmp.getEmail());

        if(user.getAddress() == null && tmp.getAddress() != null)
            user.setAddress(tmp.getAddress());

        if(user.getPhone1() == null && tmp.getPhone1() != null)
            user.setPhone1(tmp.getPhone1());

        if(user.getPhone2() == null && tmp.getPhone2() != null)
            user.setPhone2(tmp.getPhone2());

        if(user.getSkype() == null && tmp.getSkype() != null)
            user.setSkype(tmp.getSkype());

        if(user.getBirthday() == null && tmp.getBirthday() != null)
            user.setBirthday(tmp.getBirthday());

        if(user.getDescription() == null && tmp.getDescription() != null)
            user.setDescription(tmp.getDescription());

        if(user.getCurrentSchoolName() == null && tmp.getCurrentSchoolName() != null)
            user.setCurrentSchoolName(tmp.getCurrentSchoolName());

        if(user.getSchoolAccounts() == null && tmp.getSchoolAccounts() != null)
            user.setSchoolAccounts(tmp.getSchoolAccounts());

        if(user.getRoles() == null && tmp.getRoles() != null)
            user.setRoles(tmp.getRoles());

        userRepository.save(user);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void deleteCourse(User user, Course course) {
        Set<Course> courses = user.getCourses();
        for(Course c: courses)
            if(c.getName().equals(course.getName()))
                courses.remove(c);
        user.setCourses(courses);
        save(user);
    }

    @Override
    public void addCourse(User user, Course course) {
        Set<Course> courses = user.getCourses();
        courses.add(course);
        save(user);
    }

    @Override
    public void changePassword(Long id, String password) {
        User user = findById(id);
        user.setPassword(bCryptPasswordEncoder.encode(password));
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
