package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Symptom;
import com.team5.hospital_here.hospital.repository.DepartmentRepository;
import com.team5.hospital_here.hospital.repository.SymptomDepartmentRepository;
import com.team5.hospital_here.hospital.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SymptomService {

    private final SymptomRepository symptomRepository;

    public Symptom saveSymptom(String content) {
        Symptom symptom = Symptom.builder()
            .content(content)
            .build();
        return symptomRepository.save(symptom);
    }
}

