package com.team5.hospital_here.user.repository;

import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    Optional<DoctorProfile> findByUser(User user);
}
