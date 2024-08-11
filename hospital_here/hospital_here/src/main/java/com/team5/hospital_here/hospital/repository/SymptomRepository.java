package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
}
