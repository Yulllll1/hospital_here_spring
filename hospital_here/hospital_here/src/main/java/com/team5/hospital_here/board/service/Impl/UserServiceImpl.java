package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.User;
import com.team5.hospital_here.board.repository.UserRepository;
import com.team5.hospital_here.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
