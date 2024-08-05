package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.repository.DepartmentRepository;
import com.team5.hospital_here.hospital.repository.HospitalDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    public List<Department> getDepartmentsByHospitalId(Long hospitalId) {
        return hospitalDepartmentRepository.findDepartmentsByHospitalId(hospitalId);
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


}