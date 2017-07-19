package com.school.utils;

import com.school.models.EditUser;
import com.school.models.Role;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserUtils {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public EditUser userToEditUser(User user){
        EditUser editUser = new EditUser();

        editUser.setId(user.getId());
        editUser.setUsername(user.getUsername());
        editUser.setEmail(user.getEmail());
        editUser.setRole(user.getRoles().iterator().next().getName());

        return editUser;
    }

    public User editUserToUser(EditUser editUser){
        User user = userService.findById(editUser.getId());

        user.setUsername(editUser.getUsername());
        user.setEmail(editUser.getEmail());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(editUser.getRole()));

        user.setRoles(roles);

        return user;
    }

    public List<String> allRoles(){
        List<String> roles = new ArrayList<>();

        List<Role> rolesSet = new ArrayList<>(roleRepository.findAll());

        for(Role r: rolesSet)
            roles.add(r.getName());

        return roles;
    }
}
