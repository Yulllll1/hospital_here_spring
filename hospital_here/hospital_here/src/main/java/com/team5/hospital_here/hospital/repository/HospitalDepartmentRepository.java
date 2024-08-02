package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    @Query("SELECT d FROM Department d JOIN HospitalDepartment hd ON d.id = hd.department.id WHERE hd.hospital.id = :hospitalId")
    List<Department> findDepartmentsByHospitalId(@Param("hospitalId") Long hospitalId);
}