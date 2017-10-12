package com.school.repositories;

import com.school.models.ResponsiblePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {
}
