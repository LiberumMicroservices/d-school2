package com.school.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private Set<SchoolAccount> schoolAccounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<SchoolAccount> getSchoolAccounts() {
        return schoolAccounts;
    }

    public void setSchoolAccounts(Set<SchoolAccount> schoolAccounts) {
        this.schoolAccounts = schoolAccounts;
    }
}
