package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
