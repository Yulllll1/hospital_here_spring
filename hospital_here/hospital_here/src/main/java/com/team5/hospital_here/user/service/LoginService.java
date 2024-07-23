package com.team5.hospital_here.user.service;


import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.user.entity.Login;
import com.team5.hospital_here.user.entity.LoginDTO;
import com.team5.hospital_here.user.entity.User;
import com.team5.hospital_here.user.repository.LoginRepository;
import com.team5.hospital_here.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String LOGIN_SUCCESS = "로그인 성공";

    public Login findByEmail(String email){
        return loginRepository.findByEmail(email).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public String login(LoginDTO loginDTO, HttpServletResponse response){
        Login login = findByEmail(loginDTO.getEmail());
        matchPassword(loginDTO, login);

        String token = "Bearer " + jwtUtil.generateToken(loginDTO.getEmail());
        response.setHeader("Authorization", token);

        return LOGIN_SUCCESS;
    }

    private void matchPassword(LoginDTO loginDTO, Login login){


        if(!passwordEncoder.matches(loginDTO.getPassword(), login.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_PASSWORD_WRONG);
        }


    }
}
