package com.team5.hospital_here.board.repository;

import com.team5.hospital_here.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardRepository extends JpaRepository<Board, Long> {

}
