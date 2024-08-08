package com.team5.hospital_here.bookMark.service;


import com.team5.hospital_here.bookMark.entity.BookMarkDTO;
import com.team5.hospital_here.bookMark.entity.BookMarkEntity;
import com.team5.hospital_here.bookMark.entity.BookMarkMapper;
import com.team5.hospital_here.bookMark.repository.BookMarkRepository;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;

    public BookMarkService(BookMarkRepository bookMarkRepository, UserRepository userRepository, HospitalRepository hospitalRepository) {
        this.bookMarkRepository = bookMarkRepository;
        this.userRepository = userRepository;
        this.hospitalRepository = hospitalRepository;
    }


    public List<BookMarkDTO> findByUserId(Long userId) {
        List<BookMarkEntity> bookMarkEntities = bookMarkRepository.findByUserId(userId);
        List<BookMarkDTO> bookMarkDTOList = bookMarkEntities.stream()
                .map(BookMarkMapper::toBookMarkDTO)
                .toList();

        return bookMarkDTOList;

    }

    public void deleteBookMark(CustomUser customUser, Long hospitalId) {
        BookMarkEntity bookMarkEntity = bookMarkRepository.findByUserIdAndHospitalId(customUser.getUser().getId(), hospitalId);

        bookMarkRepository.delete(bookMarkEntity);
    }

    public void addBookMark(BookMarkDTO bookMarkDTO) {
        BookMarkEntity bookMarkEntity = BookMarkMapper.toBookMarkEntity(bookMarkDTO);
        Optional<User> user = userRepository.findById(bookMarkDTO.getUserId());
        bookMarkEntity.setUser(user.get());
        Optional<Hospital> hospital = hospitalRepository.findById(bookMarkDTO.getHospitalId());
        bookMarkEntity.setHospital(hospital.get());
        bookMarkRepository.save(bookMarkEntity);
    }
}
