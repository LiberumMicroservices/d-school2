package com.school.services.impl;

import com.school.models.School;
import com.school.repositories.SchoolRepository;
import com.school.services.SchoolService;
import com.school.utils.SchoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    SchoolUtils schoolUtils;

    @Override
    public void addSchool(School school) {
        schoolRepository.save(school);
    }

    @Override
    public void save(School school) {
        schoolRepository.save(school);
    }

    @Override
    public void block(School school) {
        School blockedSchool = schoolRepository.findOne(school.getId());
        blockedSchool.setBlocked(true);
        schoolRepository.save(blockedSchool);
    }

    @Override
    public void unblock(School school) {
        School blockedSchool = schoolRepository.findOne(school.getId());
        blockedSchool.setBlocked(false);
        schoolRepository.save(blockedSchool);
    }

    @Override
    public School findById(Long id) {
        return schoolRepository.findOne(id);
    }

    @Override
    public School findByName(String name) {
        return schoolRepository.findSchoolByName(name);
    }

    @Override
    public School findByPhone(String phone) {
        return schoolRepository.findSchoolByPhone(phone);
    }

    @Override
    public School findByEmail(String email) {
        return schoolRepository.findSchoolByEmail(email);
    }

    @Override
    public List<School> findAll() {
        return schoolRepository.findAll();
    }

    @Override
    public School currentSchool() {
        return findByName(schoolUtils.currentSchoolName());
    }
}
