package com.team5.hospital_here.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    EmailEntity findByEmailAndVerification(String email, String verification);
}
