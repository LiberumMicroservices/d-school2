package com.school.repositories;

import com.school.models.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
    School findSchoolByName(String name);
}
