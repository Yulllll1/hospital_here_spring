package com.team5.hospital_here.user.repository;

import com.team5.hospital_here.user.entity.Login;
import com.team5.hospital_here.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
}
