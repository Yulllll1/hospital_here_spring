package com.team5.hospital_here.user.service;


import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.login.LoginDTO;
import com.team5.hospital_here.user.repository.LoginRepository;
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

    /**
     * 이메일로 로그인 정보를 검색합니다.
     * @param email 검색할 이메일
     * @return 검색한 로그인 정보
     * @exception CustomException 존재하지 않는 로그인 정보
     */
    public Login findByEmail(String email){
        return loginRepository.findByEmail(email).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 로그인 정보가 맞는지 검증하고
     * JWT를 발행합니다.
     * @param loginDTO 검증할 로그인 정보
     * @param response JWT를 발행할 서블렛
     * @return 로그인 성공
     * @exception CustomException 존재하지 않는 로그인 정보 또는 비밀번호 매칭 실패
     */
    public String login(LoginDTO loginDTO, HttpServletResponse response){
        Login login = findByEmail(loginDTO.getEmail());
        matchPassword(loginDTO, login);

        String token = "Bearer " + jwtUtil.generateToken(loginDTO.getEmail());
        response.setHeader("Authorization", token);

        return LOGIN_SUCCESS;
    }

    /**
     * 패스워드가 일치하는지 검증합니다.
     * @param loginDTO 요청한 로그인 정보
     * @param login 데이터베이스 로그인 정보
     * @exception CustomException 비밀번호 매칭 실패
     */
    private void matchPassword(LoginDTO loginDTO, Login login){
        if(!passwordEncoder.matches(loginDTO.getPassword(), login.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_PASSWORD_WRONG);
        }
    }
}
