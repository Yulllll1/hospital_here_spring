package com.team5.hospital_here.bookMark.repository;

import com.team5.hospital_here.bookMark.entity.BookMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMarkEntity, Long> {

    List<BookMarkEntity> findByUserId(Long hospitalId);
    BookMarkEntity findByUserIdAndHospitalId(Long userId, Long hospitalId);
}
