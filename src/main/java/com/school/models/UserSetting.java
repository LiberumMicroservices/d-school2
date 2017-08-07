package com.school.models;

import java.util.List;

public class UserSetting {
    private Long id;
    private String currentSchool;
    private List<String> schoolList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentSchool() {
        return currentSchool;
    }

    public void setCurrentSchool(String currentSchool) {
        this.currentSchool = currentSchool;
    }

    public List<String> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<String> schoolList) {
        this.schoolList = schoolList;
    }
}
