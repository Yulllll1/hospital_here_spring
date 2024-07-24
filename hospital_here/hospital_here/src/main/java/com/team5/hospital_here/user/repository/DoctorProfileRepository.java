package com.team5.hospital_here.user.repository;

import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    public Optional<DoctorProfile> findByUser(User user);
}
