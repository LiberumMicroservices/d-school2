package com.school.services.impl;

import com.school.models.Role;
import com.school.models.School;
import com.school.models.SchoolAccount;
import com.school.models.User;
import com.school.repositories.SchoolAccountRepository;
import com.school.services.SchoolAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolAccountServiceImpl implements SchoolAccountService {

    @Autowired
    SchoolAccountRepository schoolAccountRepository;

    @Override
    public SchoolAccount save(SchoolAccount schoolAccount) {
        return schoolAccountRepository.save(schoolAccount);
    }

    @Override
    public SchoolAccount findById(Long id) {
        return schoolAccountRepository.findOne(id);
    }

    @Override
    public SchoolAccount setRole(SchoolAccount schoolAccount, Role role) {
        SchoolAccount sa = schoolAccountRepository.findOne(schoolAccount.getId());
        sa.setRole(role);
        schoolAccountRepository.save(sa);
        return sa;
    }

    @Override
    public List<SchoolAccount> findBySchool(School school) {
        return schoolAccountRepository.findBySchool(school);
    }

    @Override
    public List<SchoolAccount> findByUser(User user) {
        return schoolAccountRepository.findByUser(user);
    }

    @Override
    public SchoolAccount findByUserAndShool(User user, School school) {
        return schoolAccountRepository.findFirstByUserAndSchool(user, school);
    }

    @Override
    public void block(SchoolAccount schoolAccount) {
        SchoolAccount sa = schoolAccountRepository.findOne(schoolAccount.getId());
        sa.setEnabled(false);
        schoolAccountRepository.save(sa);
    }

    @Override
    public void unblock(SchoolAccount schoolAccount) {
        SchoolAccount sa = schoolAccountRepository.findOne(schoolAccount.getId());
        sa.setEnabled(true);
        schoolAccountRepository.save(sa);
    }
}
