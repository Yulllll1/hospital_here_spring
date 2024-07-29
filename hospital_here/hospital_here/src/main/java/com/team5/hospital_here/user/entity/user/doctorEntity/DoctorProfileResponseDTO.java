package com.team5.hospital_here.user.entity.user.doctorEntity;


import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.user.entity.user.UserDTO;
import lombok.Getter;


@Getter
public class DoctorProfileResponseDTO {
    private Long userId;
    private String userName;
    private Long hospitalId;
    private String hostpitalName;
    private String department;


    public DoctorProfileResponseDTO(DoctorProfile doctorProfile){
        this.userId = doctorProfile.getUser().getId();
        this.userName = doctorProfile.getUser().getName();
        this.hospitalId = doctorProfile.getHospital().getId();
        this.hostpitalName = doctorProfile.getHospital().getName();
        this.department = doctorProfile.getMajor();
    }
}
