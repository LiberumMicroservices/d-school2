package com.school.repositories;

import com.school.models.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
    School findSchoolByName(String name);
    School findSchoolByPhone(String phone);
    School findSchoolByEmail(String email);
}
