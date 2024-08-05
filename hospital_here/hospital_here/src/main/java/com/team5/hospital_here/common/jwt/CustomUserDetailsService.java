package com.team5.hospital_here.common.jwt;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import com.team5.hospital_here.user.service.UserService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("그냥 유저 디테일");
        User user = userRepository.findByLoginEmail(email).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("그냥 유저 디테일2");
        Collection<GrantedAuthority> collection =new ArrayList<>();
        collection.add(()->
            user.getRole().getName()
        );

        return new CustomUser(user, collection);
    }


}
