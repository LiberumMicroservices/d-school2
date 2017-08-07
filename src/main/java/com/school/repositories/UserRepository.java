package com.school.repositories;

import com.school.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findUserByUsername(String username);
    User findUserByPhone1(String phone1);
    User findUserByEmail(String email);
}
