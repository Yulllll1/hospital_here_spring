package com.team5.hospital_here.hospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hospital_department")
public class Hospital_department {
    @Id
    private Long hospital_id;

    @Column(nullable = false)
    private Long department_id;
}
