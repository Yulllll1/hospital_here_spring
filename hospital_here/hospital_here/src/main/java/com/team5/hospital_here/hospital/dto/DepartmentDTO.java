package com.team5.hospital_here.hospital.dto;

import com.team5.hospital_here.hospital.entity.Department;

public class DepartmentDTO {
    private Long id;
    private String name;

    public DepartmentDTO(Department department) {}

    public DepartmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}