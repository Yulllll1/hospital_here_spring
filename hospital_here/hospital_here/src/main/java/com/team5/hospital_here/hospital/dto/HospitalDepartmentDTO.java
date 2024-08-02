package com.team5.hospital_here.hospital.dto;

public class HospitalDepartmentDTO {
    private Long id;
    private HospitalDTO hospital;
    private DepartmentDTO department;

    // 기본 생성자
    public HospitalDepartmentDTO() {}

    // 모든 필드를 포함하는 생성자
    public HospitalDepartmentDTO(Long id, HospitalDTO hospital, DepartmentDTO department) {
        this.id = id;
        this.hospital = hospital;
        this.department = department;
    }

    // Getter와 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public HospitalDTO getHospital() { return hospital; }
    public void setHospital(HospitalDTO hospital) { this.hospital = hospital; }

    public DepartmentDTO getDepartment() { return department; }
    public void setDepartment(DepartmentDTO department) { this.department = department; }
}