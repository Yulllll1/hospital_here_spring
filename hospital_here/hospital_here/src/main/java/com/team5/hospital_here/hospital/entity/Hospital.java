package com.team5.hospital_here.hospital.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment 설정
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(name = "sub_district", length = 500)
    private String subDistrict;

    @Column(nullable = false)
    private String zipcode;


    @Column(name = "telephone_number", length = 500, nullable = false)
    private String telephoneNumber;

    @Column(name = "homepage_link")
    private String homepageLink;

    @Column(name = "latitude", precision = 38, scale = 7, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 38, scale = 7, nullable = false)
    private BigDecimal longitude;

    @Column(name = "sun_start_time")
    private String sunStartTime;

    @Column(name = "sun_end_time")
    private String sunEndTime;

    @Column(name = "mon_start_time")
    private String monStartTime;

    @Column(name = "mon_end_time")
    private String monEndTime;

    @Column(name = "tue_start_time")
    private String tueStartTime;

    @Column(name = "tue_end_time")
    private String tueEndTime;

    @Column(name = "wed_start_time")
    private String wedStartTime;

    @Column(name = "wed_end_time")
    private String wedEndTime;

    @Column(name = "thu_start_time")
    private String thuStartTime;

    @Column(name = "thu_end_time")
    private String thuEndTime;

    @Column(name = "fri_start_time")
    private String friStartTime;

    @Column(name = "fri_end_time")
    private String friEndTime;

    @Column(name = "sat_start_time")
    private String satStartTime;

    @Column(name = "sat_end_time")
    private String satEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getHomepageLink() {
        return homepageLink;
    }

    public void setHomepageLink(String homepageLink) {
        this.homepageLink = homepageLink;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getSunStartTime() {
        return sunStartTime;
    }

    public void setSunStartTime(String sunStartTime) {
        this.sunStartTime = sunStartTime;
    }

    public String getSunEndTime() {
        return sunEndTime;
    }

    public void setSunEndTime(String sunEndTime) {
        this.sunEndTime = sunEndTime;
    }

    public String getMonStartTime() {
        return monStartTime;
    }

    public void setMonStartTime(String monStartTime) {
        this.monStartTime = monStartTime;
    }

    public String getMonEndTime() {
        return monEndTime;
    }

    public void setMonEndTime(String monEndTime) {
        this.monEndTime = monEndTime;
    }

    public String getTueStartTime() {
        return tueStartTime;
    }

    public void setTueStartTime(String tueStartTime) {
        this.tueStartTime = tueStartTime;
    }

    public String getTueEndTime() {
        return tueEndTime;
    }

    public void setTueEndTime(String tueEndTime) {
        this.tueEndTime = tueEndTime;
    }

    public String getWedStartTime() {
        return wedStartTime;
    }

    public void setWedStartTime(String wedStartTime) {
        this.wedStartTime = wedStartTime;
    }

    public String getWedEndTime() {
        return wedEndTime;
    }

    public void setWedEndTime(String wedEndTime) {
        this.wedEndTime = wedEndTime;
    }

    public String getThuStartTime() {
        return thuStartTime;
    }

    public void setThuStartTime(String thuStartTime) {
        this.thuStartTime = thuStartTime;
    }

    public String getThuEndTime() {
        return thuEndTime;
    }

    public void setThuEndTime(String thuEndTime) {
        this.thuEndTime = thuEndTime;
    }

    public String getFriStartTime() {
        return friStartTime;
    }

    public void setFriStartTime(String friStartTime) {
        this.friStartTime = friStartTime;
    }

    public String getFriEndTime() {
        return friEndTime;
    }

    public void setFriEndTime(String friEndTime) {
        this.friEndTime = friEndTime;
    }

    public String getSatStartTime() {
        return satStartTime;
    }

    public void setSatStartTime(String satStartTime) {
        this.satStartTime = satStartTime;
    }

    public String getSatEndTime() {
        return satEndTime;
    }

    public void setSatEndTime(String satEndTime) {
        this.satEndTime = satEndTime;
    }

    @OneToMany(mappedBy = "hospital")
    private List<HospitalDepartment> hospitalDepartments;
}