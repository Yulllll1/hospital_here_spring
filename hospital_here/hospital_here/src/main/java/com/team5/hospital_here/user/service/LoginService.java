package com.team5.hospital_here.user.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.common.jwt.entity.RefreshToken;
import com.team5.hospital_here.common.jwt.repository.RefreshTokenRepository;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.login.LoginDTO;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserResponseDTO;
import com.team5.hospital_here.user.repository.LoginRepository;
import com.team5.hospital_here.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {


    private final UserService userService;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    private final String LOGIN_SUCCESS = "로그인 인증되었습니다.";
    private final String TOKEN_REFRESH_SUCCESS = "토큰이 재발급 되었습니다.";

    private final String LOGOUT_SUCCESS = "로그아웃 되었습니다.";


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
    public UserResponseDTO login(LoginDTO loginDTO, HttpServletResponse response){
        log.info("이메일 : {}",loginDTO.getEmail());

        Login login = findByEmail(loginDTO.getEmail());


        if(login.getProvider() != null)
        {
            throw new CustomException(ErrorCode.SOCIAL_USER);
        }
        matchPassword(loginDTO, login);

        RefreshToken dbToken = refreshTokenRepository.findByLogin(login).orElse(new RefreshToken());
        dbToken.setLogin(login);

        createToken(dbToken, login.getEmail(), response);

        User user = userService.findUserByEmail(login.getEmail());

        return new UserResponseDTO(user.getId(), user.getRole().name());
    }

    /**
     * 리프레시 토큰을 파기합니다.
     * @param refreshToken 파기할 토큰
     * @param response HttpServletResponse
     */
    public void logout(String refreshToken, HttpServletResponse response){
        String email = jwtUtil.getEmailFromRefreshToken(refreshToken);
        refreshTokenRepository.findByLoginEmail(email).ifPresentOrElse(
            refreshTokenRepository::delete
        , () -> {});

        destroyRefreshTokenCookie(response);
    }


    /**
     * JWT 토큰을 생성합니다.
     * @param dbToken 새로운 리프레시 토큰 또는 기존에 있던 리프레시 토큰
     * @param email 회원 이메일
     * @param response HttpServletResponse
     */
    private void createToken(RefreshToken dbToken, String email, HttpServletResponse response){
        String accessToken = "Bearer " + jwtUtil.generateAccessToken(email);
        response.setHeader("Authorization", accessToken);

        String refreshToken = jwtUtil.generateRefreshToken(email);
        Cookie cookie = new Cookie(jwtUtil.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtUtil.REFRESH_TOKEN_COOKIE_MAX_AGE);
        response.addCookie(cookie);

        dbToken.setToken(refreshToken);
        refreshTokenRepository.save(dbToken);
    }

    /**
     * 리프레시 토큰을 파기합니다.
     * @param response HttpServletResponse
     */
    private void destroyRefreshTokenCookie(HttpServletResponse response){
        Cookie cookie = new Cookie(jwtUtil.REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 패스워드가 일치하는지 검증합니다.
     * @param loginDTO 요청한 로그인 정보
     * @param login 데이터베이스 로그인 정보
     * @exception CustomException 비밀번호 매칭 실패
     */
    private void matchPassword(LoginDTO loginDTO, Login login){
        if(!passwordEncoder.matches(loginDTO.getPassword(), login.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_USER_CREDENTIALS);
        }
    }

    /**
     * 리프레시 토큰을 검증하고 
     * 새로운 엑세스 토큰 및 리프레시 토큰을 발급합니다.
     * @param refreshToken 검증할 리프레시 토큰
     * @param response HttpServletResponse
     * @return 발급 완료
     */
    public String createNewAccessToken(String refreshToken, HttpServletResponse response){
        String email = jwtUtil.getEmailFromRefreshToken(refreshToken);

        RefreshToken dbToken = refreshTokenRepository.findByLoginEmail(email).orElseThrow(()->
            new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if(!dbToken.getToken().equals(refreshToken))
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);


        createToken(dbToken, email, response);

        return TOKEN_REFRESH_SUCCESS;
    }




}
