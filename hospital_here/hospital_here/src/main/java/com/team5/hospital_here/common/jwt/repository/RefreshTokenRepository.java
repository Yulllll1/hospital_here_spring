package com.team5.hospital_here.common.jwt.repository;

import com.team5.hospital_here.common.jwt.entity.RefreshToken;
import com.team5.hospital_here.user.entity.login.Login;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByLoginEmail(String email);
    Optional<RefreshToken> findByLogin(Login login);
}
