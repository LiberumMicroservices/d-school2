package com.school.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "schools")
public class School {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String nameBrand;

    @Column
    private String phone;

    @Column
    private String address;

    private String email;

    @Column
    private String description;

    @Column
    boolean blocked;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private Set<SchoolAccount> schoolAccounts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private Set<Room> rooms;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private Set<Course> courses;

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

    public String getNameBrand() {
        return nameBrand;
    }

    public void setNameBrand(String nameBrand) {
        this.nameBrand = nameBrand;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<SchoolAccount> getSchoolAccounts() {
        return schoolAccounts;
    }

    public void setSchoolAccounts(Set<SchoolAccount> schoolAccounts) {
        this.schoolAccounts = schoolAccounts;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
