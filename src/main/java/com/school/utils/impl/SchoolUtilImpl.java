package com.school.utils.impl;

import com.school.models.School;
import com.school.models.SchoolAccount;
import com.school.models.User;
import com.school.services.SchoolAccountService;
import com.school.services.SchoolService;
import com.school.utils.SchoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SchoolUtilImpl implements SchoolUtils {

    @Autowired
    SchoolService schoolService;

    @Autowired
    private SchoolAccountService schoolAccountService;

    @Override
    public List<String> schoolList() {
        List<String> res = new ArrayList<>();
        List<School> schools = schoolService.findAll();
        res.addAll(schools.stream().map(School::getName).collect(Collectors.toList()));

        return res;
    }

    @Override
    public List<String> freeSchoolsForUserList(User user) {
        List<String> schools = schoolList();
        List<String> userSchools = new ArrayList<>(schoolsForUserList(user));

        schools.removeAll(userSchools);
        return schools;
    }

    @Override
    public List<String> schoolsForUserList(User user) {
        List<String> res = new ArrayList<>();
        List<SchoolAccount> schoolAccountsFromUser = schoolAccountService.findByUser(user);

        res.addAll(schoolAccountsFromUser.stream().map(account -> account.getSchool().getName()).collect(Collectors.toList()));

        return res;
    }
}
