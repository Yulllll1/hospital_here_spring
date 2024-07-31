package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}