package com.team5.hospital_here.board.repository;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
