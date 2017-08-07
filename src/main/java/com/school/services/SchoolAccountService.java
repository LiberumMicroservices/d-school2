package com.school.services;

import com.school.models.Role;
import com.school.models.School;
import com.school.models.SchoolAccount;
import com.school.models.User;

import java.util.List;

public interface SchoolAccountService {

    SchoolAccount save(SchoolAccount schoolAccount);
    SchoolAccount findById(Long id);
    SchoolAccount setRole(SchoolAccount schoolAccount, Role role);
    List<SchoolAccount> findBySchool(School school);
    List<SchoolAccount> findByUser(User user);
    SchoolAccount findByUserAndShool(User user, School school);
    void block(SchoolAccount schoolAccount);
    void unblock(SchoolAccount schoolAccount);
}
