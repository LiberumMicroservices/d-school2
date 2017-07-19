package com.school.services;

import com.school.models.School;

import java.util.List;

public interface SchoolService {

    void addSchool(School school);
    void save(School school);
    void block(School school);
    void unblock(School school);
    School findById(Long id);
    School findByName(String name);
    School findByPhone(String phone);
    School findByEmail(String email);
    List<School> findAll();


}
