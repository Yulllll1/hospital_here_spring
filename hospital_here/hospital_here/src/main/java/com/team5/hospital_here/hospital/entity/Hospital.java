package com.team5.hospital_here.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

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



    @OneToMany(mappedBy = "hospital")
    private List<HospitalDepartment> hospitalDepartments;
}