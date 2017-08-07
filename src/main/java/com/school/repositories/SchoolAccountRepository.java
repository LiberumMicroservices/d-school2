package com.school.repositories;

import com.school.models.School;
import com.school.models.SchoolAccount;
import com.school.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolAccountRepository extends JpaRepository<SchoolAccount, Long> {
    List<SchoolAccount> findBySchool(School school);
    List<SchoolAccount> findByUser(User user);
    SchoolAccount findFirstByUserAndSchool(User user, School school);
}
