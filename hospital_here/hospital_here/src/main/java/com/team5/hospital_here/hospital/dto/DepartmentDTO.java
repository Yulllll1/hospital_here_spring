package com.team5.hospital_here.hospital.dto;

public class DepartmentDTO {
    private Long id;
    private String name;

    // 기본 생성자
    public DepartmentDTO() {}

    // 모든 필드를 포함하는 생성자
    public DepartmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter와 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}