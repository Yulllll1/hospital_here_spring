package com.team5.hospital_here.board.repository;

import com.team5.hospital_here.board.domain.PostImg;
import com.team5.hospital_here.board.dto.postImg.PostImgResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {

}
