package com.team5.hospital_here.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HospitalDTO {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private String district;
    private String subDistrict;
    private String telephoneNumber;
    private List<DepartmentDTO> departments = new ArrayList<>();
    private Double distance;
    private boolean isOpenNow;

    private String sunStartTime;
    private String sunEndTime;
    private String monStartTime;
    private String monEndTime;
    private String tueStartTime;
    private String tueEndTime;
    private String wedStartTime;
    private String wedEndTime;
    private String thuStartTime;
    private String thuEndTime;
    private String friStartTime;
    private String friEndTime;
    private String satStartTime;
    private String satEndTime;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String getOpenStatusMessage() {
        return isOpenNow ? "영업 중" : "영업 종료";
    }

    public HospitalDTO(Long id, String name, Double latitude, Double longitude, String address, String district, String subDistrict, String telephoneNumber, List<DepartmentDTO> departments,
                       String sunStartTime, String sunEndTime,
                       String monStartTime, String monEndTime,
                       String tueStartTime, String tueEndTime,
                       String wedStartTime, String wedEndTime,
                       String thuStartTime, String thuEndTime,
                       String friStartTime, String friEndTime,
                       String satStartTime, String satEndTime) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.district = district;
        this.subDistrict = subDistrict;
        this.telephoneNumber = telephoneNumber;
        this.departments = departments != null ? departments : new ArrayList<>();
        this.sunStartTime = sunStartTime;
        this.sunEndTime = sunEndTime;
        this.monStartTime = monStartTime;
        this.monEndTime = monEndTime;
        this.tueStartTime = tueStartTime;
        this.tueEndTime = tueEndTime;
        this.wedStartTime = wedStartTime;
        this.wedEndTime = wedEndTime;
        this.thuStartTime = thuStartTime;
        this.thuEndTime = thuEndTime;
        this.friStartTime = friStartTime;
        this.friEndTime = friEndTime;
        this.satStartTime = satStartTime;
        this.satEndTime = satEndTime;
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
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
    public boolean isOpenNow() { return isOpenNow; }
    public void setOpenNow(boolean isOpenNow) { this.isOpenNow = isOpenNow; }

    // Getter and setter methods for time strings
    public String getSunStartTime() { return sunStartTime; }
    public void setSunStartTime(String sunStartTime) { this.sunStartTime = sunStartTime; }
    public String getSunEndTime() { return sunEndTime; }
    public void setSunEndTime(String sunEndTime) { this.sunEndTime = sunEndTime; }
    public String getMonStartTime() { return monStartTime; }
    public void setMonStartTime(String monStartTime) { this.monStartTime = monStartTime; }
    public String getMonEndTime() { return monEndTime; }
    public void setMonEndTime(String monEndTime) { this.monEndTime = monEndTime; }
    public String getTueStartTime() { return tueStartTime; }
    public void setTueStartTime(String tueStartTime) { this.tueStartTime = tueStartTime; }
    public String getTueEndTime() { return tueEndTime; }
    public void setTueEndTime(String tueEndTime) { this.tueEndTime = tueEndTime; }
    public String getWedStartTime() { return wedStartTime; }
    public void setWedStartTime(String wedStartTime) { this.wedStartTime = wedStartTime; }
    public String getWedEndTime() { return wedEndTime; }
    public void setWedEndTime(String wedEndTime) { this.wedEndTime = wedEndTime; }
    public String getThuStartTime() { return thuStartTime; }
    public void setThuStartTime(String thuStartTime) { this.thuStartTime = thuStartTime; }
    public String getThuEndTime() { return thuEndTime; }
    public void setThuEndTime(String thuEndTime) { this.thuEndTime = thuEndTime; }
    public String getFriStartTime() { return friStartTime; }
    public void setFriStartTime(String friStartTime) { this.friStartTime = friStartTime; }
    public String getFriEndTime() { return friEndTime; }
    public void setFriEndTime(String friEndTime) { this.friEndTime = friEndTime; }
    public String getSatStartTime() { return satStartTime; }
    public void setSatStartTime(String satStartTime) { this.satStartTime = satStartTime; }
    public String getSatEndTime() { return satEndTime; }
    public void setSatEndTime(String satEndTime) { this.satEndTime = satEndTime; }

}