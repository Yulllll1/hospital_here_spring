package com.team5.hospital_here.common.jwt;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.exception.ExceptionResponse;
import com.team5.hospital_here.user.entity.User;
import com.team5.hospital_here.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null)
        {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getLogin().getEmail(),
                user.getLogin().getPassword(),
                new ArrayList<>()
        );
    }
}
