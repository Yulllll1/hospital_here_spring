package com.team5.hospital_here.hospital.dto;

import java.math.BigDecimal;
import java.util.List;

public class HospitalDTO {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private String district;
    private String subDistrict;
    private String telephoneNumber;
    private List<DepartmentDTO> departments; // 부서 리스트 추가

    public HospitalDTO() {}

    public HospitalDTO(Long id, String name, Double latitude, Double longitude, String address, String district, String subDistrict, String telephoneNumber, List<DepartmentDTO> departments) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.district = district;
        this.subDistrict = subDistrict;
        this.telephoneNumber = telephoneNumber;
        this.departments = departments;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getSubDistrict() { return subDistrict; }
    public void setSubDistrict(String subDistrict) { this.subDistrict = subDistrict; }
    public String getTelephoneNumber() { return telephoneNumber; }
    public void setTelephoneNumber(String telephoneNumber) { this.telephoneNumber = telephoneNumber; }
    public List<DepartmentDTO> getDepartments() { return departments; }
    public void setDepartments(List<DepartmentDTO> departments) { this.departments = departments; }
}