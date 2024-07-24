package com.team5.hospital_here.user.repository;

import com.team5.hospital_here.user.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginEmail(String email);
}
