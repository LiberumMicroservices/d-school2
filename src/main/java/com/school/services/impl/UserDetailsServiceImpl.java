package com.school.services.impl;

import com.school.models.Role;
import com.school.models.School;
import com.school.models.User;
import com.school.repositories.SchoolRepository;
import com.school.repositories.UserRepository;
import com.school.services.SchoolAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    SchoolAccountService schoolAccountService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for(Role role: user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        try {
            School school = schoolRepository.findSchoolByName(user.getCurrentSchoolName());
            Role roleSchool = schoolAccountService.findByUserAndShool(user, school).getRole();
            grantedAuthorities.add(new SimpleGrantedAuthority(roleSchool.getName()));
        } catch (Exception e){}

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, grantedAuthorities);
    }
}
