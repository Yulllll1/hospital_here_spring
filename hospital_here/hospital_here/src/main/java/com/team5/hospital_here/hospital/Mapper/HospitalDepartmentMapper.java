package com.team5.hospital_here.hospital.Mapper;

import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import org.springframework.stereotype.Component;

@Component
public class HospitalDepartmentMapper {

    public HospitalDepartmentDTO convertToDto(HospitalDepartment hospitalDepartment) {
        // HospitalDTO 객체 생성
        Hospital hospital = hospitalDepartment.getHospital();
        HospitalDTO hospitalDTO = new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getLatitude() != null ? hospital.getLatitude().doubleValue() : null,
                hospital.getLongitude() != null ? hospital.getLongitude().doubleValue() : null,
                hospital.getAddress(),
                hospital.getCity()
        );

        // DepartmentDTO 객체 생성
        Department department = hospitalDepartment.getDepartment();
        DepartmentDTO departmentDTO = new DepartmentDTO(
                department.getId(),
                department.getName()
        );

        // HospitalDepartmentDTO 객체 생성
        return new HospitalDepartmentDTO(
                hospitalDepartment.getId(),
                hospitalDTO,
                departmentDTO
        );
    }
}