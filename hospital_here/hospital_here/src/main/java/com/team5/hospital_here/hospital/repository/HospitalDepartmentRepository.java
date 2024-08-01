package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    List<HospitalDepartment> findByHospitalId(Long hospitalId);
}