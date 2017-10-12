package com.school.models;

import java.util.List;

public class AddStudentForm {

    private User user;

    private List<ResponsiblePerson> responsiblePersons;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ResponsiblePerson> getResponsiblePersons() {
        return responsiblePersons;
    }

    public void setResponsiblePersons(List<ResponsiblePerson> responsiblePersons) {
        this.responsiblePersons = responsiblePersons;
    }
}
