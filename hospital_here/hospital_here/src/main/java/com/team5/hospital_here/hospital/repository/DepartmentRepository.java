package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d FROM Department d JOIN HospitalDepartment hd ON d.id = hd.department.id WHERE hd.hospital.id = :hospitalId")
    List<Department> findByHospitalId(@Param("hospitalId") Long hospitalId);

    // 진료과 이름으로 조회하는 메서드
    Department findByName(String name);
}