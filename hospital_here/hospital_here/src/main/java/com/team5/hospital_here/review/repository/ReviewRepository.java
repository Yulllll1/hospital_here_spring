package com.team5.hospital_here.review.repository;


import com.team5.hospital_here.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByUserId(Long id);
    List<ReviewEntity> findByHospitalId(Long id);

    Page<ReviewEntity> findByUserId(Long userId, Pageable pageable);
    Page<ReviewEntity> findByHospitalId(Long hospitalId, Pageable pageable);
}
