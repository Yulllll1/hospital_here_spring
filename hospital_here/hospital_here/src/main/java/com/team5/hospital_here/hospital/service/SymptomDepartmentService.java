package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.Symptom;
import com.team5.hospital_here.hospital.entity.SymptomDepartment;
import com.team5.hospital_here.hospital.repository.DepartmentRepository;
import com.team5.hospital_here.hospital.repository.SymptomDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SymptomDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final SymptomDepartmentRepository symptomDepartmentRepository;

    public void saveSymptomDepartments(Symptom symptom, Set<String> departmentNames) {
        for (String name : departmentNames) {
            Department department = departmentRepository.findByName(name);

            if (department != null) {
                SymptomDepartment symptomDepartment = SymptomDepartment.builder()
                    .symptom(symptom)
                    .department(department)
                    .build();
                symptomDepartmentRepository.save(symptomDepartment);
            }
        }
    }
}
