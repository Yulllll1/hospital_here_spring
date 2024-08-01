package com.team5.hospital_here.hospital.repository;


import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Page<Hospital> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(String name, String address, Pageable pageable);
}