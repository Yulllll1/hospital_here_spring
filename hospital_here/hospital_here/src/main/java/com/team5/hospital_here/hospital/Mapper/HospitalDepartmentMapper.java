package com.team5.hospital_here.hospital.Mapper;

import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class HospitalDepartmentMapper {

    public HospitalDepartmentDTO convertToDto(HospitalDepartment hospitalDepartment) {
        Department department = hospitalDepartment.getDepartment();
        DepartmentDTO departmentDTO = new DepartmentDTO(department.getId(), department.getName());

        Hospital hospital = hospitalDepartment.getHospital();
        HospitalDTO hospitalDTO = convertToDto(hospital);

        return new HospitalDepartmentDTO(
                hospitalDepartment.getId(),
                hospitalDTO,
                departmentDTO
        );
    }

    // Hospital 엔티티를 HospitalDTO로 변환
    public HospitalDTO convertToDto(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getLatitude() != null ? hospital.getLatitude() : null,
                hospital.getLongitude() != null ? hospital.getLongitude() : null,
                hospital.getAddress(),
                hospital.getDistrict(),
                hospital.getSubDistrict(),
                hospital.getTelephoneNumber(),
                new ArrayList<>()
        );
    }
}