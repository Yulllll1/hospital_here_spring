package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //departments 리스트만
    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    //hospitalId에 따른 매핑된 부서별 정보
    @GetMapping("/hospitals/{hospitalId}/departments")
    public ResponseEntity<List<Department>> getDepartmentsByHospitalId(@PathVariable Long hospitalId) {
        List<Department> departments = departmentService.getDepartmentsByHospitalId(hospitalId);
        return ResponseEntity.ok(departments);
    }
}