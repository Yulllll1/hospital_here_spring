package com.team5.hospital_here.hospital.Mapper;

import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDTO convertToDto(Department department) {
        // Department를 DepartmentDTO로 변환하는 로직
        return new DepartmentDTO(department.getId(), department.getName());
    }
}